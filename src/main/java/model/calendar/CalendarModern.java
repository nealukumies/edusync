package model.calendar;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class CalendarModern extends VBox {

    public CalendarModern() {
        CalendarSchedules schedules = new CalendarSchedules(1/*STUDENT ID*/);
        LocalDate today = LocalDate.now();

        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(0);
        calendarGrid.setVgap(0);
        int daysToShow = 7;

        for (int col = 0; col < daysToShow; col++) {
            Label dateLabel = new Label(today.plusDays(col).toString());
            calendarGrid.add(dateLabel, col+1, 0);
        }

        for (int row = 0; row < 24; row++) {
            Label timeLabel = new Label(String.format("%02d:00", row));
            calendarGrid.add(timeLabel, 0, row+1);
        }

        for (int col = 0; col < daysToShow; col++) {
            if(schedules.hasSchedules(today.plusDays(col))){
                System.out.println("idk");
            }
            for (int row = 0; row < 24; row++) {
                VBox cell = new VBox();
                cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
                calendarGrid.add(cell, col+1, row+1);
            }
        }
        this.getChildren().add(calendarGrid);
    }
}