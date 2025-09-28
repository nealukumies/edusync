package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.DBObjects.Assignment;
import model.DBObjects.Course;
import model.DBObjects.DBObjectParser;
import model.DBObjects.Schedule;
import model.Enums.Status;
import model.Singletons.Account;
import model.handlers.AssignmentHandler;
import model.handlers.CourseHandler;
import model.handlers.ScheduleHandler;
import model.handlers.UserHandler;

import java.util.ArrayList;

public class testMain {

  public static void main(String[] args) throws JsonProcessingException {
    System.out.println(
      UserHandler.loginUser("katti@matikainen.fi", "salasana"));
      AssignmentHandler.updateAssignment(307, 174, "Homework 1", "Very important math homework","2024-09-15 13:30:17", "completed");
      //AssignmentHandler.createAssignment(174, "Homework 1", "Basics of C++", "2024-09-15 13:30:15");
      ArrayList<Assignment> assignmentList = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());
      System.out.println(assignmentList);
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
//AssignmentHandler.createAssignment(174, "Homework 1", "2024-09-15 13:30:15")
//AssignmentHandler.getAssignment(237).body()
//AssignmentHandler.getAssignmentsForUser().body()
//AssignmentHandler.updateAssignment(237, "Homework 1", "Very important math homework","2024-09-15 13:30:16", "completed")
//AssignmentHandler.deleteAssignment(1)
//OBJECT PARSER TESTS
//Student student = DBObjectParser.parseStudent(UserHandler.getUser())
//ArrayList<Course> courseList = DBObjectParser.parseCourseList(CourseHandler.getCourses())
//Course course = DBObjectParser.parseCourse(CourseHandler.getCourse(174))
//Schedule schedule = DBObjectParser.parseSchedule(ScheduleHandler.getSchedule(145))
//ArrayList<Schedule> scheduleList = DBObjectParser.parseScheduleList(ScheduleHandler.getSchedulesForUser())
//ArrayList<Schedule> scheduleList = DBObjectParser.parseScheduleList(ScheduleHandler.getSchedulesForCourse(174))
//Assignment assignment = DBObjectParser.parseAssignment(AssignmentHandler.getAssignment(237))
//ArrayList<Assignment> assignmentList = DBObjectParser.parseAssignmentList(AssignmentHandler.getAssignmentsForUser());