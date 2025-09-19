//package model;
//
//import database.AssignmentDao;
//import database.CourseDao;
//import database.StudentDao;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//public class testMain {
//    public static void main(String[] args) {
//        AssignmentDao assignmentDao = new AssignmentDao();
//
//        List<Assignment> assignments = assignmentDao.getAssignments(1);
//
//        for (Assignment assignment : assignments) {
//            System.out.println(assignment);
//        }
//
//        //StudentDao studentDao = new StudentDao();
//        //int newStudentId = studentDao.addStudent("Katti Matikainen", "Katti.Matikainen@katti.org");
//        //System.out.println("New student ID: " + newStudentId);
//
//        //AssignmentDao assignmentDao = new AssignmentDao();
//        //assignmentDao.insertAssignment(32, 1, "Physics Homework", "Complete exercises 1-10", Date.valueOf("2025-10-11"));
//
//        //CourseDao courseDao = new CourseDao();
//        //List<Course> courses = courseDao.getCourses(32);
//    }
//}
