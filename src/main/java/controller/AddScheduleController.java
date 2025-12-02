package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.enums.Weekday;
import model.handlers.ScheduleHandler;

public class AddScheduleController extends SubController {
    @FXML
    private ChoiceBox<String> courseSelect;
    @FXML
    private ChoiceBox<Weekday> weekDaySelect;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;

    @FXML
    private Button submit;
    @FXML
    private Button cancel;

    public void initialize() {
        weekDaySelect.getItems().setAll(Weekday.values());
        weekDaySelect.setValue(Weekday.MONDAY);
    }

    @Override
    public void initializeFully() {
        courseSelect.getItems().setAll(getMainController().getCourse().getCourseName());
        courseSelect.setValue(getMainController().getCourse().getCourseName());

        submit.setOnAction(e -> addSchedule());

        cancel.setOnAction(e -> getMainController().goToPrevPage());
    }

    public void addSchedule() {
        final String day = weekDaySelect.getValue().toString();
        final int[] timeStartInt = parseTimeString(startTime.getText());
        final int[] timeEndInt = parseTimeString(endTime.getText());
        final String startString = String.format("%02d", timeStartInt[0]) + ":" + String.format("%02d", timeStartInt[1]) + ":" + String.format("%02d", 0);
        final String endString = String.format("%02d", timeEndInt[0]) + ":" + String.format("%02d", timeEndInt[1]) + ":" + String.format("%02d", 0);
        try {
            ScheduleHandler.createSchedule(getMainController().getCourse().getCourseId(), day, startString, endString);
            getMainController().goToPrevPage();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to add schedule", e);
        }
    }

    public int[] parseTimeString(final String ts) {
        final int[] time = new int[2];
        AssignmentUtility.parseTimeString(ts, time);
        return time;
    }
}
