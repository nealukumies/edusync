package controller;

import enums.Page;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.handlers.CourseHandler;
import view.MainView;

/**
 * Utility class for course-related operations.
 */
public class CourseUtility {
    private CourseUtility() {}

    /**
     * Creates or updates a course based on the provided method.
     *
     * @param method          The method to use ("create" or "update").
     * @param title           The TextField for the course title.
     * @param startDate      The DatePicker for the start date.
     * @param endDate        The DatePicker for the end date.
     * @param mainController The main controller for page navigation.
     * @param errorMessage   The Label to display error messages.
     */
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

    /**
     * Validates that the title is not empty.
     *
     * @param title The TextField for the course title.
     * @return true if the title is valid, false otherwise.
     */
    public static boolean validateTitle(final TextField title) {
        return !title.getText().trim().isEmpty();
    }

    /**
     * Validates that the start date is not null.
     *
     * @param startDate The DatePicker for the start date.
     * @return true if the start date is valid, false otherwise.
     */
    public static boolean validateStartDate(final DatePicker startDate) {
        return startDate.getValue() != null;
    }

    /**
     * Validates that the end date is not null.
     *
     * @param endDate The DatePicker for the end date.
     * @return true if the end date is valid, false otherwise.
     */
    public static boolean validateEndDate(final DatePicker endDate) {
        return endDate.getValue() != null;
    }
}
