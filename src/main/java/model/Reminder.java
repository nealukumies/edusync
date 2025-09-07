package model;

import java.util.Date;

public class Reminder {
    private int reminderId;
    private int studentId;
    private String message;
    private Date reminderTime;

    public Reminder(int reminderId, int studentId, String message, Date reminderTime) {
        this.reminderId = reminderId;
        this.studentId = studentId;
        this.message = message;
        this.reminderTime = reminderTime;
    }
}
