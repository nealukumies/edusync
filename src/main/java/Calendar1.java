import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class Calendar1 extends VBox {
    public Calendar1() {
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // Add listener
        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            System.out.println("Selected date: " + selectedDate);
        });

        this.getChildren().add(datePicker);
    }
}