package controller;

import enums.Page;
import model.db_objects.Assignment;
import model.db_objects.Course;

public class PageMemento {
    private Page page;
    private Course course;
    private Assignment assignment;

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
