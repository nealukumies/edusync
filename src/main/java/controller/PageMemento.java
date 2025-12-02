package controller;

import enums.Page;
import model.db_objects.Assignment;
import model.db_objects.Course;

/** Memento class to store the state of the current page, course, and assignment. */
public class PageMemento {
    /** The current page. */
    private Page page;
    /** The course associated with the page, if any. */
    private Course course;
    /** The assignment associated with the page, if any. */
    private Assignment assignment;

    /** Constructor for PageMemento.
     *
     * @param page       The current page.
     * @param course     The current course (can be null).
     * @param assignment The current assignment (can be null).
     */
    public PageMemento(final Page page, final Course course, final Assignment assignment) {
        this.page = page;
        this.course = course;
        this.assignment = assignment;
    }

    public Page getPage() {
        return page;
    }

    public Course getCourse() {
        return course;
    }

    public Assignment getAssignment() {
        return assignment;
    }
}
