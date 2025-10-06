package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Enums.Weekday;
import model.handlers.ScheduleHandler;

public class AddScheduleController extends SubController {
    @FXML
    private ChoiceBox courseSelect;
    @FXML
    private ChoiceBox weekDaySelect;
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

        submit.setOnAction(e -> {
            addSchedule();
        });

        cancel.setOnAction(e -> {
            getMainController().goToPrevPage();
        });
    }

    public void addSchedule() {
        String day = weekDaySelect.getValue().toString();
        int[] _timeStart = parseTimeString(startTime.getText());
        int[] _timeEnd = parseTimeString(endTime.getText());
        String _start = String.format("%02d", _timeStart[0]) + ":" + String.format("%02d", _timeStart[1]) + ":" + String.format("%02d", 0);
        String _end = String.format("%02d", _timeEnd[0]) + ":" + String.format("%02d", _timeEnd[1]) + ":" + String.format("%02d", 0);
        try {
            ScheduleHandler.createSchedule(getMainController().getCourse().getCourseId(), day, _start, _end);
            getMainController().goToPrevPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] parseTimeString(String ts) {
        int[] time = new int[2];
        if (ts.contains(":")) {
            try {
                String[] chars = ts.split(":");
                int hours = Integer.parseInt(chars[0]);
                int minutes = Integer.parseInt(chars[1]);
                time[0] = hours;
                time[1] = minutes;
                if (hours > 23 || hours < 0) {
                    time[0] = 0;
                }
                if (minutes > 59 || minutes < 0) {
                    time[1] = 0;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                time[0] = 0;
                time[1] = 0;
            }
        }
        return time;
    }
}
