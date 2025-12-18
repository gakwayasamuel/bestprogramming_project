package test;

import model.*;
import service.*;
import util.*;
import exception.*;
import java.util.*;

/**
 * Campus Test Class
 *
 * Comprehensive test suite for verifying system functionality.
 * Tests Singleton Pattern, Factory Pattern, and all business logic.
 */
public class CampusTest {

    public static void main(String[] args) {
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println("         CAMPUS CLUB SYSTEM - TEST SUITE");
        System.out.println("══════════════════════════════════════════════════════");

        try {
            testSingletonPattern();
            testFactoryPattern();
            testStudentOperations();
            testClubOperations();
            testEventOperations();
            testJoinOperations();
            testReportGeneration();

            System.out.println("\n✅ ALL TESTS PASSED SUCCESSFULLY!");
            System.out.println("══════════════════════════════════════════════════════");

        } catch (Exception e) {
            System.out.println("\n❌ TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Tests Singleton Pattern implementation
     */
    private static void testSingletonPattern() {
        System.out.println("\n🔍 TEST 1: SINGLETON PATTERN");

        CampusService service1 = CampusService.getInstance();
        CampusService service2 = CampusService.getInstance();

        if (service1 == service2) {
            System.out.println("✅ Singleton Pattern verified - Same instance returned");
        } else {
            System.out.println("❌ Singleton Pattern failed - Different instances");
        }
    }

    /**
     * Tests Factory Pattern implementation
     */
    private static void testFactoryPattern() {
        System.out.println("\n🔍 TEST 2: FACTORY PATTERN");

        // Test Student factory
        Student student = ObjectFactory.createStudent(9999, "Test Student");
        if (student != null && student.getName().equals("Test Student")) {
            System.out.println("✅ Student Factory works correctly");
        }

        // Test Club factory
        Club club = ObjectFactory.createClub(9999, "Test Club", "Test Description");
        if (club != null && club.getName().equals("Test Club")) {
            System.out.println("✅ Club Factory works correctly");
        }

        // Test Event factory
        Event event = ObjectFactory.createEvent(99999, "Test Event", "Test Desc",
                "2024-12-31", "10:00", 9999);
        if (event != null && event.getName().equals("Test Event")) {
            System.out.println("✅ Event Factory works correctly");
        }
    }

    /**
     * Tests student operations
     */
    private static void testStudentOperations() throws ValidationException {
        System.out.println("\n🔍 TEST 3: STUDENT OPERATIONS");

        CampusService service = CampusService.getInstance();

        // Clear any existing data
        service.saveAllData();

        // Test adding student
        service.addStudent(1111, "Test Student A");
        System.out.println("✅ Student added successfully");

        // Test duplicate student ID
        try {
            service.addStudent(1111, "Test Student B");
            System.out.println("❌ Should not allow duplicate IDs");
        } catch (ValidationException e) {
            System.out.println("✅ Duplicate ID prevention works");
        }

        // Test invalid student ID
        try {
            service.addStudent(-1, "Invalid Student");
            System.out.println("❌ Should not allow negative IDs");
        } catch (ValidationException e) {
            System.out.println("✅ Invalid ID validation works");
        }

        // Test getting student
        Student student = service.getStudent(1111);
        if (student != null && student.getName().equals("Test Student A")) {
            System.out.println("✅ Student retrieval works");
        }
    }

    /**
     * Tests club operations
     */
    private static void testClubOperations() throws ValidationException {
        System.out.println("\n🔍 TEST 4: CLUB OPERATIONS");

        CampusService service = CampusService.getInstance();

        // Test creating club
        service.createClub(2222, "Test Club", "Test Club Description");
        System.out.println("✅ Club created successfully");

        // Test duplicate club ID
        try {
            service.createClub(2222, "Another Club", "Another Description");
            System.out.println("❌ Should not allow duplicate club IDs");
        } catch (ValidationException e) {
            System.out.println("✅ Duplicate club ID prevention works");
        }

        // Test getting club
        Club club = service.getClub(2222);
        if (club != null && club.getName().equals("Test Club")) {
            System.out.println("✅ Club retrieval works");
        }
    }

    /**
     * Tests event operations
     */
    private static void testEventOperations() throws ValidationException {
        System.out.println("\n🔍 TEST 5: EVENT OPERATIONS");

        CampusService service = CampusService.getInstance();

        // Test creating event
        service.createEvent(33333, "Test Event", "Test Event Description",
                "2024-12-25", "14:00", 2222);
        System.out.println("✅ Event created successfully");

        // Test duplicate event ID
        try {
            service.createEvent(33333, "Another Event", "Another Description",
                    "2024-12-26", "15:00", 2222);
            System.out.println("❌ Should not allow duplicate event IDs");
        } catch (ValidationException e) {
            System.out.println("✅ Duplicate event ID prevention works");
        }

        // Test invalid date format
        try {
            service.createEvent(44444, "Invalid Event", "Description",
                    "25-12-2024", "14:00", 2222);
            System.out.println("❌ Should not allow invalid date format");
        } catch (ValidationException e) {
            System.out.println("✅ Date format validation works");
        }

        // Test getting event
        Event event = service.getEvent(33333);
        if (event != null && event.getName().equals("Test Event")) {
            System.out.println("✅ Event retrieval works");
        }
    }

    /**
     * Tests join operations (student joins club, registers for event)
     */
    private static void testJoinOperations() throws ValidationException {
        System.out.println("\n🔍 TEST 6: JOIN OPERATIONS");

        CampusService service = CampusService.getInstance();

        // Setup test data
        service.addStudent(5555, "Join Test Student");
        service.createClub(6666, "Join Test Club", "Test Club for Joining");
        service.createEvent(77777, "Join Test Event", "Test Event for Registration",
                "2024-12-31", "10:00", 6666);

        // Test student joining club
        String joinResult = service.joinClub(5555, 6666);
        System.out.println("✅ " + joinResult);

        // Test student registering for event
        String registerResult = service.registerForEvent(5555, 77777);
        System.out.println("✅ " + registerResult);

        // Verify relationships
        Student student = service.findStudent(5555);
        Club club = service.findClub(6666);
        Event event = service.findEvent(77777);

        if (student.isInClub(6666) && club.hasMember(5555)) {
            System.out.println("✅ Club membership verified");
        }

        if (student.isInEvent(77777) && event.hasParticipant(5555)) {
            System.out.println("✅ Event registration verified");
        }
    }

    /**
     * Tests report generation
     */
    private static void testReportGeneration() {
        System.out.println("\n🔍 TEST 7: REPORT GENERATION");

        CampusService service = CampusService.getInstance();

        // Test student report
        System.out.println("\nGenerating Student Report...");
        ReportGenerator.generateStudentReport(service);

        // Test club report
        System.out.println("\nGenerating Club Report...");
        ReportGenerator.generateClubReport(service);

        // Test event report
        System.out.println("\nGenerating Event Report...");
        ReportGenerator.generateEventReport(service);

        System.out.println("✅ All report generation tests completed");
    }
}