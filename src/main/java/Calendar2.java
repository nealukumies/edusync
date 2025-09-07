import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.YearMonth;

public class Calendar2 extends VBox {

    public Calendar2() {
        YearMonth currentMonth = YearMonth.now(); // current month
        LocalDate firstOfMonth = currentMonth.atDay(1);
        int daysInMonth = currentMonth.lengthOfMonth();

        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);

        // Weekday headers
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, i, 0);
        }

        // Fill days
        int dayOfWeekIndex = firstOfMonth.getDayOfWeek().getValue() % 7; // Sunday = 0
        int day = 1;
        for (int row = 1; row <= 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 1 && col < dayOfWeekIndex) continue;
                if (day > daysInMonth) break;

                Button dayButton = new Button(String.valueOf(day));
                dayButton.setPrefSize(40, 40);
                calendarGrid.add(dayButton, col, row);

                day++;
            }
        }

        // Add grid to this VBox (the component itself)
        this.getChildren().add(calendarGrid);
    }
}
