package controller;

import enums.Page;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.handlers.CourseHandler;
import view.MainView;

public class CourseUtility {
    private CourseUtility() {}

    public static void createCourse(final String method, final TextField title, final DatePicker startDate, final DatePicker endDate, final MainController mainController, final Label errorMessage) {
        if (validateTitle(title) && validateStartDate(startDate) && validateEndDate(endDate)) {
            final String titleString = title.getText();
            final String startDateString = startDate.getValue().toString();
            final String endDateString = endDate.getValue().toString();
            try {
                if (method.equals("update")) {
                    CourseHandler.updateCourse(mainController.getCourse().getCourseId(), titleString, startDateString, endDateString);
                } else if (method.equals("create")) {
                    CourseHandler.createCourse(titleString, startDateString, endDateString);
                }
                mainController.changePage(Page.COURSE_LIST_PAGE);
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }
        } else {
            final String errormessage = MainView.getBundle().getString("course_errortext");
            errorMessage.setText(errormessage);
        }
    }

    public static boolean validateTitle(final TextField title) {
        return !title.getText().trim().isEmpty();
    }

    public static boolean validateStartDate(final DatePicker startDate) {
        return startDate.getValue() != null;
    }

    public static boolean validateEndDate(final DatePicker endDate) {
        return endDate.getValue() != null;
    }
}
