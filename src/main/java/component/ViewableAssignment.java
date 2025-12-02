package component;

import model.db_objects.Assignment;
import model.db_objects.Course;

import java.sql.Timestamp;

/**
 * Wrapper class for Assignment to provide additional view-related information.
 */
public class ViewableAssignment {
    /** Assignment object */
    private Assignment assignment;
    /** Course object associated with the assignment */
    private Course course;

    /** Constructor for ViewableAssignment
     *
     * @param assignment Assignment object
     * @param course     Course object associated with the assignment
     */
    public ViewableAssignment(final Assignment assignment, final Course course) {
        this.assignment = assignment;
        this.course = course;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    /**  Returns the Course object associated with the assignment.
     */
    public Course getCourseObject() {
        return course;
    }

    /**  Returns the title of the assignment.
     */
    public String getTitle() {
        return assignment.getTitle();
    }

    public Integer getCourse() {
        return course.getCourseId();
    }

    /**  Returns the deadline of the assignment.
     */
    public Timestamp getDeadline() {
        return assignment.getDeadline();
    }

    /**  Returns the status of the assignment as a string.
     */
    public String getStatus() {
        return assignment.getStatus().toString();
    }
}
