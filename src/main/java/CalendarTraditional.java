import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class CalendarTraditional extends VBox {

    public CalendarTraditional() {
        GridPane calendarGrid = new GridPane();


        String[] weekdays = {"mon", "tue", "wed", "thu", "fri", "sat", "sun"};
        for(int day = 0; day < 7; day++){
            Label dayLabel = new Label(weekdays[day]);
            dayLabel.setStyle("-fx-font-weight: bold;");
            calendarGrid.add(dayLabel, day+1, 0);
        }

        LocalDate today = LocalDate.now();
        int day = 1;
        int row = 1;

        calendarGrid.setHgap(10);
        calendarGrid.setVgap(10);

        do{
            for(int col = 1; col <= 7; col++){
                if (row == 1 && col < today.withDayOfMonth(1).getDayOfWeek().getValue()) continue;
                if (day <= today.lengthOfMonth()){
                    Button dayButton = new Button(String.valueOf(day));
                    dayButton.setOnAction(e -> {
                        System.out.println(dayButton.getText());
                    });
                    dayButton.setStyle("-fx-font-weight: bold;");
                    dayButton.setPrefSize(40, 40);
                    calendarGrid.add(dayButton, col, row);
                    day++;
                }else {
                    break;
                }
            }
            row++;
        }while(day <= today.lengthOfMonth());

        this.getChildren().add(calendarGrid);
    }
}
