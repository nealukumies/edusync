package model.db_objects;

import model.enums.Weekday;

import java.time.LocalTime;

/** * Represents a schedule entry for a course.
 */
public class Schedule {
    /** The unique identifier for the schedule entry.
     */
    private int scheduleId;
    /** The identifier for the associated course.
     */
    private int courseId;
    /** The day of the week for the schedule entry.
     */
    private Weekday weekday;
    /** The start time of the schedule entry.
     */
    private LocalTime startTime;
    /** The end time of the schedule entry.
     */
    private LocalTime endTime;

    /** Constructor for Schedule.
     *
     * @param scheduleId The unique identifier for the schedule entry.
     * @param courseId   The identifier for the associated course.
     * @param weekday    The day of the week for the schedule entry.
     * @param startTime  The start time of the schedule entry.
     * @param endTime    The end time of the schedule entry.
     */
    public Schedule(final int scheduleId, final int courseId, final Weekday weekday, final LocalTime startTime, final LocalTime endTime) {
        this.scheduleId = scheduleId;
        this.courseId = courseId;
        this.weekday = weekday;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // For debugging purposes
    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", courseId=" + courseId +
                ", weekday=" + weekday +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    public int getScheduleId() {
        return scheduleId;
    }
    public int getCourseId() {
        return courseId;
    }
    public Weekday getWeekday() {
        return weekday;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
}
