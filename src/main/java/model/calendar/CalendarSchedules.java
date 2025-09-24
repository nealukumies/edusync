package model.calendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarSchedules {
    public List<CalendarSchedule> schedules = new ArrayList<>();
    public List<CalendarSchedule> courses = new ArrayList<>();
    public LocalDate earliestStartDate;
    public LocalDate latestEndDate;



    List<CalendarSchedule> scheduleTestList = new ArrayList<>();
    List<CalendarCourse> courseTestList = new ArrayList<>();
    public void testData(){
        scheduleTestList.add(new CalendarSchedule(1, 1, "MONDAY", 10, 12));
        scheduleTestList.add(new CalendarSchedule(2, 2, "WEDNESDAY", 14, 16));
        courseTestList.add(new CalendarCourse(1, 1, "math", LocalDate.of(2025,9,24), LocalDate.of(2025, 10, 25)));
        courseTestList.add(new CalendarCourse(2, 2, "Physics", LocalDate.of(2024,9,24), LocalDate.of(2024, 10, 25)));

    }



    public CalendarSchedules(int studentId) {
        // schedules.addAll(GET /schedules/students/{studentId})
        testData();
        int counter = 0;

        for(CalendarSchedule schedule : scheduleTestList){
            schedule.setCalendarCourse(courseTestList.get(counter));
            counter++;
            /*
            schedule.setCourseName("Mathematics");
            schedule.setStartDate(LocalDate.of(2025, 9,24));
            schedule.setEndDate(LocalDate.of(2025, 10,25));
             */
            if(earliestStartDate==null || schedule.getCalendarCourse().getStartDate().isBefore(earliestStartDate)){
                earliestStartDate = schedule.getCalendarCourse().getStartDate();
            }
            if(latestEndDate==null || schedule.getCalendarCourse().getEndDate().isAfter(latestEndDate)){
                latestEndDate = schedule.getCalendarCourse().getEndDate();
            }
            schedules.add(schedule);
        }
        //schedules.addAll(scheduleTestList);
    }

    public List<CalendarSchedule> getSchedules() {
        return schedules;
    }

    public boolean hasSchedules(LocalDate date) {
        return !date.isBefore(this.earliestStartDate) && !date.isAfter(this.latestEndDate);
    }
}