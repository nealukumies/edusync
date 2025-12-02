package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.enums.Weekday;
import model.handlers.ScheduleHandler;

/**
 * Controller for adding a new schedule entry.
 */
public class AddScheduleController extends SubController {
    /** ChoiceBox for selecting the course */
    @FXML
    private ChoiceBox<String> courseSelect;
    /** ChoiceBox for selecting the weekday */
    @FXML
    private ChoiceBox<Weekday> weekDaySelect;
    /** TextField for start time input */
    @FXML
    private TextField startTime;
    /** TextField for end time input */
    @FXML
    private TextField endTime;
    /** Button to submit the new schedule */
    @FXML
    private Button submit;
    /** Button to cancel adding the schedule */
    @FXML
    private Button cancel;

    /** Initializes the controller by populating the weekday selection. */
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

    /**
     * Adds a new schedule entry based on user input.
     */
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

    /**
     * Parses a time string in the format "HH:MM" into an array of integers.
     *
     * @param ts the time string to parse
     * @return an array where the first element is the hour and the second is the minute
     */
    public int[] parseTimeString(final String ts) {
        final int[] time = new int[2];
        AssignmentUtility.parseTimeString(ts, time);
        return time;
    }
}
