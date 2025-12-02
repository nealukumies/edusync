package model.calendar;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.db_objects.Course;
import model.db_objects.DBObjectParser;
import model.db_objects.Schedule;
import model.enums.Weekday;
import model.handlers.CourseHandler;
import model.handlers.ScheduleHandler;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A modern calendar view that displays schedules and courses in a grid format.
 */
public class CalendarModern extends VBox {

    /** List of schedules for the user. */
    private List<Schedule> schedules = new ArrayList<>();
    /** List of courses associated with the schedules. */
    private List<Course> courses = new ArrayList<>();
    /** Map of course IDs to course names for quick lookup. */
    private final Map<Integer, String> courseNameById = new HashMap<>();
    /** The current weekday being processed. */
    Weekday weekday;

    List<String> checkSchedule(final int col, final int row){
        weekday = checkWeekday(col);
        final List<String> courseNames = new ArrayList<>();
        for (final Schedule schedule : schedules){
            if(schedule.getWeekday() == weekday && schedule.getStartTime().getHour() <= row && row < schedule.getEndTime().getHour()){
                    for(final Course course : courses){
                        if(course.getCourseId() == schedule.getCourseId()){
                            courseNames.add(course.getCourseName());
                        }
                    }
                }

        }
        return courseNames;
    }

    Weekday checkWeekday(final int col){
        return switch ((LocalDate.now().getDayOfWeek().getValue() - 1 + col) % 7) {
            case 0 -> Weekday.MONDAY;
            case 1 -> Weekday.TUESDAY;
            case 2 -> Weekday.WEDNESDAY;
            case 3 -> Weekday.THURSDAY;
            case 4 -> Weekday.FRIDAY;
            case 5 -> Weekday.SATURDAY;
            case 6 -> Weekday.SUNDAY;
            default -> null;
        };
    }


    /** * Constructs a CalendarModern instance, fetching schedules and courses, and building the calendar grid. */
    public CalendarModern() {
        final HttpResponse<String> scheduleResponse = ScheduleHandler.getSchedulesForUser();
        if (scheduleResponse != null) {
            loadSchedules(scheduleResponse);
        }

        final HttpResponse<String> courseResponse = CourseHandler.getCourses();
        if (courseResponse != null) {
            loadCourses(courseResponse);
        }

        buildCalendarGrid(scheduleResponse != null);
    }

    private void loadSchedules(final HttpResponse<String> scheduleResponse) {
        try {
            schedules = DBObjectParser.parseScheduleList(scheduleResponse);
        } catch (JsonProcessingException e) {
            throw new CalendarDataException("Failed to parse schedule list", e);
        }
    }

    private void loadCourses(final HttpResponse<String> courseResponse) {
        try {
            courses = DBObjectParser.parseCourseList(courseResponse);
            populateCourseMap();
        } catch (JsonProcessingException e) {
            throw new CalendarDataException("Failed to parse course list", e);
        }
    }

    private void populateCourseMap() {
        courseNameById.clear();
        for (final Course c : courses) {
            courseNameById.put(c.getCourseId(), c.getCourseName());
        }
    }

    private void buildCalendarGrid(final boolean hasScheduleResponse) {
        this.setPrefSize(1200, 600);
        final GridPane calendarGrid = new GridPane();
        calendarGrid.setPrefSize(1200, 600);
        calendarGrid.setHgap(0);
        calendarGrid.setVgap(0);

        for (int col = 0; col < 7; col++) {
            final Label dateLabel = new Label(LocalDate.now().plusDays(col).format(DateTimeFormatter.ofPattern("EEE d/M")));
            dateLabel.setWrapText(true);
            dateLabel.setStyle("-fx-text-fill: #ffffff");
            calendarGrid.add(dateLabel, col + 1, 0);
        }

        for (int row = 0; row < 24; row++) {
            final Label timeLabel = new Label(String.format("%02d:00", row));
            timeLabel.setStyle("-fx-text-fill: #ffffff");
            calendarGrid.add(timeLabel, 0, row + 1);
        }

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 24; row++) {
                final VBox cell = createCell(hasScheduleResponse, col, row);
                calendarGrid.add(cell, col + 1, row + 1);
            }
        }
        this.getChildren().add(calendarGrid);
    }

    private VBox createCell(final boolean hasScheduleResponse, final int col, final int row) {
        final VBox cell = new VBox();
        if (hasScheduleResponse) {
            final List<String> courseNames = checkSchedule(col, row);
            if (!courseNames.isEmpty()) {
                for (final String courseName : courseNames) {
                    final Label label = new Label(courseName);
                    label.setStyle("-fx-text-fill: #000000");
                    cell.getChildren().add(label);
                }
                cell.setStyle("-fx-border-color: black; -fx-min-height: 30px; -fx-min-width: 80px; -fx-background-color: lightgray;");
            } else {
                cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
            }
        } else {
            cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
        }
        return cell;
    }
}