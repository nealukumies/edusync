package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Enums.Status;
import model.Singletons.Account;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;
import model.handlers.ScheduleHandler;
import model.handlers.UserHandler;

public class testMain {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(UserHandler.loginUser("katti@matikainen.fi", "salasana"));
        Account.getInstance().clearAccount();

        System.out.println(UserHandler.getUser().body());
    }
}


//USER HANDLER TESTS
//UserHandler.loginUser("katti@matikainen.fi", "salasana")
//UserHandler.updateUser("Katti", "katti@matikainen.fi")
//UserHandler.registerUser("Matti", "katti@matikainen.fi", "salasana")
//UserHandler.deleteUser()
//UserHandler.getUser().body()

//COURSE HANDLER TESTS
//CourseHandler.createCourse("Economics 101", "2024-07-01", "2024-12-31")
//CourseHandler.getCourses().body()
//CourseHandler.getCourse(174).body()
//CourseHandler.updateCourse(174, "Economics 102", "2024-07-01", "2024-12-31")
//CourseHandler.deleteCourse(175)

//SCHEDULE HANDLER TESTS
//ScheduleHandler.createSchedule(174, "Monday", "10:00", "12:00")
//ScheduleHandler.getSchedule(142).body()
//ScheduleHandler.getSchedulesForUser().body()
//ScheduleHandler.getSchedulesForCourse(174).body()
//ScheduleHandler.updateSchedule(142, "Monday", "11:00", "13:00")
//ScheduleHandler.deleteSchedule(142)

//ASSIGNMENT HANDLER TESTS
//AssignmentHandler.createAssignment(174, "Homework 1", "2024-09-15")
//AssignmentHandler.getAssignment(237).body()
//AssignmentHandler.getAssignmentsForUser().body()
//AssignmentHandler.updateAssignment(237, "Homework 1", "Very important math homework","2024-09-15, Status.COMPLETED")
//AssignmentHandler.deleteAssignment(1)