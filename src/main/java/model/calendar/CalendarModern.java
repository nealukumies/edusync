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
import java.util.List;

public class CalendarModern extends VBox {

    public List<Schedule> schedules = new ArrayList<>();
    public List<Course> courses = new ArrayList<>();
    Weekday weekday;

    List checkSchedule(int col, int row){
        weekday = checkWeekday(col);
        List<String> courseNames = new ArrayList<>();
        for (Schedule schedule : schedules){
            if(schedule.getWeekday() == weekday && schedule.getStartTime().getHour() <= row && row < schedule.getEndTime().getHour()){
                    for(Course course : courses){
                        if(course.getCourseId() == schedule.getCourseId()){
                            courseNames.add(course.getCourseName());
                        }
                    }
                }

        }
        return courseNames;
    }

    Weekday checkWeekday(int col){
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


    public CalendarModern() {
        HttpResponse<String> scheduleResponse = ScheduleHandler.getSchedulesForUser();
        if(scheduleResponse != null){
            try {
                schedules = DBObjectParser.parseScheduleList(scheduleResponse);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        HttpResponse<String> courseResponse = CourseHandler.getCourses();
        if(courseResponse != null){
            try{
                courses = DBObjectParser.parseCourseList(courseResponse);
            }catch(JsonProcessingException e){
                throw new RuntimeException(e);
            }
        }


        this.setPrefSize(1200, 600);
        GridPane calendarGrid = new GridPane();
        calendarGrid.setPrefSize(1200, 600);
        calendarGrid.setHgap(0);
        calendarGrid.setVgap(0);

        for (int col = 0; col < 7; col++) {
            Label dateLabel = new Label(
                    LocalDate.now().plusDays(col).format(DateTimeFormatter.ofPattern("EEE d/M"))
            );
            dateLabel.setWrapText(true);
            dateLabel.setStyle("-fx-text-fill: #ffffff");
            calendarGrid.add(dateLabel, col+1, 0);
        }

        for (int row = 0; row < 24; row++) {
            Label timeLabel = new Label(String.format("%02d:00", row));
            timeLabel.setStyle("-fx-text-fill: #ffffff");
            calendarGrid.add(timeLabel, 0, row+1);
        }

        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 24; row++) {
                VBox cell = new VBox();
                if(scheduleResponse != null){
                    List courseNames = checkSchedule(col, row);
                    if(!courseNames.isEmpty()) {
                        for(Object courseName: courseNames){
                            Label label = new Label(courseName.toString());
                            label.setStyle("-fx-text-fill: #000000");
                            cell.getChildren().add(label);
                        }
                        cell.setStyle("-fx-border-color: black; -fx-min-height: 30px; -fx-min-width: 80px; -fx-background-color: lightgray;");
                    }else{
                        cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
                    }

                }else {
                    cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
                }
                calendarGrid.add(cell, col+1, row+1);
            }
        }
        this.getChildren().add(calendarGrid);
    }
}