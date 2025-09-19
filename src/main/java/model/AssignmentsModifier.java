//package model;
//
//import database.AssignmentDao;
//
//import java.sql.Date;
//
//public class AssignmentsModifier {
//    public static int addAssignment(int userId, int courseId, String title, String description, String deadline) {
//        AssignmentDao assignmentDao = new AssignmentDao();
//        int assignmentId = assignmentDao.insertAssignment(userId, courseId, title, description, Date.valueOf(deadline));
//        return assignmentId;
//    }
//
//    public static boolean removeAssignment(int assignmentId) {
//        AssignmentDao assignmentDao = new AssignmentDao();
//        return assignmentDao.deleteAssignment(assignmentId);
//    }
//
//    public static boolean changeAssignmentStatus(int assignmentId, Status status) {
//        AssignmentDao assignmentDao = new AssignmentDao();
//        return assignmentDao.setStatus(assignmentId, status);
//    }
//}