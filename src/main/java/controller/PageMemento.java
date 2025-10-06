package controller;

import enums.Page;
import model.DBObjects.Assignment;
import model.DBObjects.Course;

public class PageMemento {
    private Page page;
    private Course course;
    private Assignment assignment;

    public PageMemento(Page page, Course course, Assignment assignment) {
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
