package app;

import model.*;
import service.*;
import util.*;
import exception.*;
import java.util.*;

/**
 * Main Application - MVC VIEW LAYER
 *
 * This class handles user interaction and displays the console interface.
 * It follows MVC pattern where this class represents the VIEW component.
 *
 * Responsibilities:
 * 1. Display menu to users
 * 2. Get user input
 * 3. Call appropriate service methods
 * 4. Display results
 */
public class Main {
    // Get the Singleton instance of CampusService
    private static CampusService campusService = CampusService.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   CAMPUS CLUB & EVENT MANAGEMENT SYSTEM (JAVA)     ║");
        System.out.println("║           WITH DESIGN PATTERNS IMPLEMENTED          ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Load existing data from JSON files
        System.out.println("\nLoading system data...");
        campusService.loadAllData();

        System.out.println("\n✓ System Initialized Successfully!");
        displaySystemInfo();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = InputHelper.getInt("Enter your choice (0-11): ", 0, 11);

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    createClub();
                    break;
                case 3:
                    studentJoinClub();
                    break;
                case 4:
                    createEvent();
                    break;
                case 5:
                    registerForEvent();
                    break;
                case 6:
                    displayAllStudents();
                    break;
                case 7:
                    displayAllClubs();
                    break;
                case 8:
                    displayAllEvents();
                    break;
                case 9:
                    ReportGenerator.generateStudentReport(campusService);
                    break;
                case 10:
                    ReportGenerator.generateClubReport(campusService);
                    break;
                case 11:
                    ReportGenerator.generateEventReport(campusService);
                    break;
                case 0:
                    saveAndExit();
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please try again.");
            }

            if (running && choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
        System.out.println("\nThank you for using Campus Club Management System!");
    }

    /**
     * Displays system information (counts of students, clubs, events)
     */
    private static void displaySystemInfo() {
        System.out.println("📊 Current System Status:");
        System.out.println("   👥 Students: " + campusService.getStudentCount());
        System.out.println("   🏢 Clubs: " + campusService.getClubCount());
        System.out.println("   🎉 Events: " + campusService.getEventCount());
        System.out.println("────────────────────────────────────────────────");
    }

    /**
     * Displays the main menu with all available options
     */
    private static void displayMainMenu() {
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("                   MAIN MENU");
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println("1.  ➕ Add New Student");
        System.out.println("2.  🏢 Create New Club");
        System.out.println("3.  🤝 Student Join Club");
        System.out.println("4.  🎉 Create New Event");
        System.out.println("5.  📝 Register for Event");
        System.out.println("6.  👥 View All Students");
        System.out.println("7.  🏢 View All Clubs");
        System.out.println("8.  🎉 View All Events");
        System.out.println("9.  📊 Generate Student Report");
        System.out.println("10. 📋 Generate Club Report");
        System.out.println("11. 📈 Generate Event Report");
        System.out.println("0.  🚪 Exit Program");
        System.out.println("══════════════════════════════════════════════════════");
    }

    /**
     * Handles adding a new student to the system
     */
    private static void addStudent() {
        System.out.println("\n────────── ADD NEW STUDENT ──────────");
        int id = InputHelper.getInt("Enter Student ID: ");
        String name = InputHelper.getString("Enter Student Name: ");

        try {
            campusService.addStudent(id, name);
            System.out.println("✅ Student '" + name + "' added successfully!");
        } catch (ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Handles creating a new club
     */
    private static void createClub() {
        System.out.println("\n────────── CREATE NEW CLUB ──────────");
        int id = InputHelper.getInt("Enter Club ID: ");
        String name = InputHelper.getString("Enter Club Name: ");
        String description = InputHelper.getString("Enter Club Description: ");

        try {
            campusService.createClub(id, name, description);
            System.out.println("✅ Club '" + name + "' created successfully!");
        } catch (ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Handles student joining a club
     */
    private static void studentJoinClub() {
        System.out.println("\n────────── STUDENT JOIN CLUB ──────────");
        int studentId = InputHelper.getInt("Enter Student ID: ");
        int clubId = InputHelper.getInt("Enter Club ID to join: ");

        try {
            String result = campusService.joinClub(studentId, clubId);
            System.out.println("✅ " + result);
        } catch (ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Handles creating a new event
     */
    private static void createEvent() {
        System.out.println("\n────────── CREATE NEW EVENT ──────────");
        int id = InputHelper.getInt("Enter Event ID: ");
        String name = InputHelper.getString("Enter Event Name: ");
        String description = InputHelper.getString("Enter Event Description: ");
        String date = InputHelper.getDate("Enter Event Date (YYYY-MM-DD): ");
        String time = InputHelper.getTime("Enter Event Time (HH:MM): ");
        int clubId = InputHelper.getInt("Enter Hosting Club ID: ");

        try {
            campusService.createEvent(id, name, description, date, time, clubId);
            System.out.println("✅ Event '" + name + "' created successfully!");
        } catch (ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Handles student registration for an event
     */
    private static void registerForEvent() {
        System.out.println("\n────────── REGISTER FOR EVENT ──────────");
        int studentId = InputHelper.getInt("Enter Student ID: ");
        int eventId = InputHelper.getInt("Enter Event ID: ");

        try {
            String result = campusService.registerForEvent(studentId, eventId);
            System.out.println("✅ " + result);
        } catch (ValidationException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Displays all students in the system
     */
    private static void displayAllStudents() {
        System.out.println("\n────────── ALL STUDENTS ──────────");
        List<Student> students = campusService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        System.out.println("Total Students: " + students.size());
        System.out.println("─────────────────────────────────");

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". 👤 " + s.getName() +
                    " (ID: " + s.getId() +
                    " | Clubs: " + s.getClubIds().size() +
                    " | Events: " + s.getEventIds().size() + ")");
        }
    }

    /**
     * Displays all clubs in the system
     */
    private static void displayAllClubs() {
        System.out.println("\n────────── ALL CLUBS ──────────");
        List<Club> clubs = campusService.getAllClubs();

        if (clubs.isEmpty()) {
            System.out.println("No clubs in the system.");
            return;
        }

        System.out.println("Total Clubs: " + clubs.size());
        System.out.println("──────────────────────────────");

        for (int i = 0; i < clubs.size(); i++) {
            Club c = clubs.get(i);
            System.out.println((i + 1) + ". 🏢 " + c.getName() +
                    " (ID: " + c.getId() +
                    " | Members: " + c.getMemberIds().size() +
                    " | Events: " + c.getEventIds().size() + ")");
            System.out.println("   📝 " + c.getDescription());
        }
    }

    /**
     * Displays all events in the system
     */
    private static void displayAllEvents() {
        System.out.println("\n────────── ALL EVENTS ──────────");
        List<Event> events = campusService.getAllEvents();

        if (events.isEmpty()) {
            System.out.println("No events in the system.");
            return;
        }

        System.out.println("Total Events: " + events.size());
        System.out.println("───────────────────────────────");

        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            System.out.println((i + 1) + ". 🎉 " + e.getName() +
                    " (ID: " + e.getId() +
                    " | Date: " + e.getDate() + " " + e.getTime() +
                    " | Participants: " + e.getParticipantIds().size() + ")");
        }
    }

    /**
     * Saves all data and exits the program
     */
    private static void saveAndExit() {
        System.out.println("\nSaving all data to files...");
        campusService.saveAllData();
        System.out.println("✅ All data saved successfully!");
    }
}
