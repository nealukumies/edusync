//package model;
//
//import java.util.Scanner;
//import java.util.List;
//
//public class TextInterface implements ControlInterface {
//    private List<Assignment> assignments;
//    private final Scanner sc = new Scanner(System.in);
//    int userId;
//
//    @Override
//    public int handleLogin() {
//        System.out.println("Handling login via text interface...");
//
//        do {
//            System.out.print("Enter your email: ");
//            String email = sc.nextLine();
//
//            userId = Login.tryLogin(email);
//
//            if (userId == -1) {
//                System.out.println("Login failed. Please try again.");
//            }
//
//        } while (userId == -1);
//
//        System.out.println("Login successful! User ID: " + userId);
//        System.out.println("--------------------------------------------------------------------------------");
//
//        return userId;
//    }
//
//    @Override
//    public void handleGetData(int userId) {
//        System.out.println("Fetching data for user ID: " + userId);
//        assignments = DataFetcher.fetchUserAssignments(userId);
//
//        System.out.println("--------------------------------------------------------------------------------");
//
//        System.out.println("Assignments:");
//        for (Assignment assignment : assignments) {
//            System.out.println(assignment);
//        }
//
//        System.out.println("--------------------------------------------------------------------------------");
//
//        System.out.println("Courses:");
//        for (Assignment assignment : assignments) {
//            System.out.println(DataFetcher.fetchCourse(assignment.getCourseId()));
//        }
//
//        System.out.println("--------------------------------------------------------------------------------");
//    }
//
//    @Override
//    public void displayOptions() {
//        String option;
//
//        do {
//            System.out.println("Options:");
//            System.out.println("1. add Assignment");
//            System.out.println("2. remove Assignment");
//            System.out.println("3. change Assignment status");
//            System.out.println("4. print Assignments and courses");
//            System.out.println("Type 'exit' to quit.");
//            System.out.print("Choose an option: ");
//            option = sc.nextLine();
//            System.out.println("--------------------------------------------------------------------------------");
//
//            switch (option) {
//                case "1":
//                    UIaddAssignment();
//                    break;
//                case "2":
//                    UIremoveAssignment();
//                    break;
//                case "3":
//                    UIupdateAssignmentStatus();
//                    break;
//                case "4":
//                    handleGetData(userId);
//                    break;
//                case "exit":
//                    System.out.println("Exiting...");
//                    break;
//                default:
//                    System.out.println("Invalid option. Please try again.");
//                    break;
//            }
//
//        } while (!option.equals("exit"));
//    }
//
//    public void UIaddAssignment() {
//        System.out.println("Enter course id: ");
//        int courseId = Integer.parseInt(sc.nextLine());
//
//        System.out.println("Enter course title: ");
//        String title = sc.nextLine();
//
//        System.out.println("Enter course description: ");
//        String description = sc.nextLine();
//
//        System.out.println("Enter course deadline (YYYY-MM-DD): ");
//        String deadline = sc.nextLine();
//
//        int assignmentId = AssignmentsModifier.addAssignment(userId, courseId, title, description, deadline);
//
//        if (assignmentId != -1) {
//            System.out.println("Assignment added successfully with ID: " + assignmentId);
//        } else {
//            System.out.println("Failed to add assignment.");
//        }
//    }
//
//    public void UIremoveAssignment() {
//        System.out.println("Enter assignment id: ");
//        int assignmentId = Integer.parseInt(sc.nextLine());
//
//        boolean success = AssignmentsModifier.removeAssignment(assignmentId);
//
//        if (success) {
//            System.out.println("Assignment removed successfully.");
//        } else {
//            System.out.println("Failed to remove assignment.");
//        }
//    }
//
//    public void UIupdateAssignmentStatus() {
//        System.out.println("Enter assignment id: ");
//        int assignmentId = Integer.parseInt(sc.nextLine());
//
//        int i;
//        String input;
//
//        do {
//            i = 1;
//
//            System.out.println("Select new status:");
//            for (Status status : Status.values()) {
//                System.out.println(i++ + ". " + status);
//            }
//
//            System.out.print("Enter the number corresponding to the new status: ");
//
//            input = sc.nextLine();
//
//        } while (Integer.parseInt(input) < 1 || Integer.parseInt(input) > Status.values().length);
//
//        boolean success = AssignmentsModifier.changeAssignmentStatus(assignmentId, Status.values()[Integer.parseInt(input)-1]);
//
//        if (success) {
//            System.out.println("Assignment status changed successfully.");
//        } else {
//            System.out.println("Failed to change assignment status.");
//        }
//    }
//}
