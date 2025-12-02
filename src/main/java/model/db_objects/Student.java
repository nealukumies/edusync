package model.db_objects;

/** Represents a student with an ID, name, and email. */
public class Student {
    /** The student's ID. */
    private int id;
    /** The student's name. */
    private String name;
    /** The student's email. */
    private String email;

    /** Constructor for Student.
     *
     * @param id    The student's ID.
     * @param name  The student's name.
     * @param email The student's email.
     */
    public Student(final int id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student id=" + id + ", name='" + name + "', email='" + email + "'}";
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
