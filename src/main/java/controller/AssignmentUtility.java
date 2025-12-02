package controller;

import component.CourseOption;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.db_objects.Course;
import model.handlers.AssignmentHandler;

import java.time.LocalDate;
import java.util.List;

public class AssignmentUtility {
    private AssignmentUtility() {}

    public static void populateCourseList(ChoiceBox<CourseOption> courseSelect, List<Course> courses , MainController mainController) {
        courseSelect.getItems().clear();
        for (final Course c : courses) {
            final CourseOption option = new CourseOption(c.getCourseId(), c.getCourseName());
            courseSelect.getItems().add(option);
        }
        if (mainController.getCourse() != null) {
            final CourseOption selected = new CourseOption(mainController.getCourse().getCourseId(), mainController.getCourse().getCourseName());
            courseSelect.setValue(selected);
        }
    }

    public static void parseTimeString(String ts, int[] time) {
        if (ts.contains(":")) {
            try {
                final String[] chars = ts.split(":");
                final int hours = Integer.parseInt(chars[0]);
                final int minutes = Integer.parseInt(chars[1]);
                time[0] = hours;
                time[1] = minutes;
                if (hours > 23 || hours < 0) {
                    time[0] = 0;
                }
                if (minutes > 59 || minutes < 0) {
                    time[1] = 0;
                }
            } catch (Exception e) {
                time[0] = 0;
                time[1] = 0;
            }
        }
    }

    public static void submitAssignment(DatePicker dateSelect, TextField timeSelect, int[] time, ChoiceBox<CourseOption> courseSelect, TextField title, TextArea desc, MainController mainController) {
        if (dateSelect.getValue() == null) {
            return;
        }

        try {
            parseTimeString(timeSelect.getText(), time);
            final CourseOption courseOption = courseSelect.getValue();
            final String titleString = title.getText();
            final String descString = desc.getText();
            final LocalDate dateLocalDate = dateSelect.getValue();
            final String timeString = String.format("%02d", time[0]) + ":" + String.format("%02d", time[1]) + ":" + String.format("%02d", 0);
            final String dateTimeString = dateLocalDate.toString() + " " + timeString;
            if (mainController.getAssignment() == null) {
                AssignmentHandler.createAssignment(courseOption.getId(), titleString, descString, dateTimeString);
            } else {
                AssignmentHandler.updateAssignment(mainController.getAssignment().getAssignmentId(), courseOption.getId(), titleString, descString, dateTimeString, mainController.getAssignment().getStatus().getDbValue());
            }
            mainController.goToPrevPage();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to create assigment", e);
        }
    }
}
