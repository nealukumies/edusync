package model.calendar;
import com.fasterxml.jackson.core.JsonProcessingException;
import model.DBObjects.DBObjectParser;
import model.DBObjects.Schedule;
import model.Singletons.Account;
import model.Singletons.Connection;
import model.handlers.ScheduleHandler;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarSchedules {
    public List<Schedule> schedules = new ArrayList<>();
    Connection conn = Connection.getInstance();
    int studentId = Account.getInstance().getStudentId();




    /*
    //List<CalendarSchedule> scheduleTestList = new ArrayList<>();
    //List<CalendarCourse> courseTestList = new ArrayList<>();
    public void testData(){
        scheduleTestList.add(new CalendarSchedule(1, 1, "MONDAY", 10, 12));
        scheduleTestList.add(new CalendarSchedule(2, 2, "WEDNESDAY", 12, 16));
        courseTestList.add(new CalendarCourse(1, 1, "math", LocalDate.of(2025,9,24), LocalDate.of(2025, 9, 30)));
        courseTestList.add(new CalendarCourse(2, 2, "Physics", LocalDate.of(2025,9,24), LocalDate.of(2025, 9, 29)));
    }

     */



    public CalendarSchedules() {
        //String endpoint = "/schedules/students/" + studentId;
        HttpResponse<String> response = ScheduleHandler.getSchedulesForUser();
        if(response != null){
            try {
                schedules = DBObjectParser.parseScheduleList(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        //HttpResponse<String> response = conn.sendGetRequest(endpoint);
        //this.schedules = DBObjectParser.parseScheduleList(response);
        //schedules = res

        // schedules.addAll(GET /schedules/students/{studentId})
        //testData();
        /*
        int counter = 0;

        for(CalendarSchedule schedule : scheduleTestList){
            schedule.setCalendarCourse(courseTestList.get(counter));
            counter++;
            schedules.add(schedule);
        }

         */
    }



    public List<Schedule> getSchedules() {
        return schedules;
    }
/*
    public List hasSchedules(LocalDate date, int clock) {
        List <String> courseNames = new ArrayList<>();
        for(Schedule schedule : schedules){
            if(schedule.getCalendarCourse().isDuringCourse(date) && schedule.isDuringSchedule(clock)){
                courseNames.add(schedule.getCalendarCourse().getCourseName());
            }
        }
        return courseNames;
    }

 */
}