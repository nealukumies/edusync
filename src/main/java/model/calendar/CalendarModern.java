package model.calendar;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class CalendarModern extends VBox {

    public CalendarModern() {
        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(0);
        calendarGrid.setVgap(0);

        LocalDate today = LocalDate.now();
        int daysToShow = 7;

        for (int col = 1; col <= daysToShow; col++) {
            Label dateLabel = new Label(today.plusDays(col - 1).toString());
            calendarGrid.add(dateLabel, col, 0);
        }

        for (int row = 1; row <= 24; row++) {
            Label timeLabel = new Label(String.format("%02d:00", row - 1));
            calendarGrid.add(timeLabel, 0, row);
        }

        for (int col = 1; col <= daysToShow; col++) {
            for (int row = 1; row <= 24; row++) {
                VBox cell = new VBox();
                cell.setStyle("-fx-border-color: lightgray; -fx-min-height: 30px; -fx-min-width: 80px;");
                calendarGrid.add(cell, col, row);
            }
        }
        this.getChildren().add(calendarGrid);
    }
}