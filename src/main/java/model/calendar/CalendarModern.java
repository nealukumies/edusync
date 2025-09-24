package model.calendar;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class CalendarModern extends VBox {

    public CalendarModern() {
        CalendarSchedules schedules = new CalendarSchedules();
        LocalDate today = LocalDate.now();

        GridPane calendarGrid = new GridPane();
        calendarGrid.setStyle("-fx-background-color: #000000");
        calendarGrid.setHgap(0);
        calendarGrid.setVgap(0);
        int daysToShow = 7;

        for (int col = 0; col < daysToShow; col++) {
            Label dateLabel = new Label(today.plusDays(col).toString());
            dateLabel.setStyle("-fx-text-fill: #ffffff");
            calendarGrid.add(dateLabel, col+1, 0);
        }

        for (int row = 0; row < 24; row++) {
            Label timeLabel = new Label(String.format("%02d:00", row));
            timeLabel.setStyle("-fx-text-fill: #ffffff");
            calendarGrid.add(timeLabel, 0, row+1);
        }

        for (int col = 0; col < daysToShow; col++) {
            int clock = 0;
            for (int row = 0; row < 24; row++) {
                VBox cell = new VBox();
                List courseNames = schedules.hasSchedules(today.plusDays(col), clock);
                if(!courseNames.isEmpty()){
                    cell.setStyle("-fx-border-color: black; -fx-min-height: 30px; -fx-min-width: 80px; -fx-background-color: lightgray;");
                    for (Object name : courseNames){
                        Label label = new Label(name.toString());
                        cell.getChildren().add(label);
                    }

                }else {
                    cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
                }
                calendarGrid.add(cell, col+1, row+1);
                clock++;
            }
        }
        this.getChildren().add(calendarGrid);
    }
}