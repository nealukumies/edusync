package component;

/**
 * Represents a course option with an ID and name.
 */
public class CourseOption {
    /** Course ID */
    private int id;
    /** Course name */
    private String courseName;

    /** Constructor for CourseOption
     *
     * @param id         Course ID
     * @param courseName Course name
     */
    public CourseOption(final int id, final String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
