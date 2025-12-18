package service;

import model.*;
import util.*;
import exception.*;
import java.util.*;

/**
 * Campus Service Class - MVC CONTROLLER LAYER with SINGLETON PATTERN
 *
 * SINGLETON PATTERN IMPLEMENTATION:
 * - Private static instance variable
 * - Private constructor to prevent external instantiation
 * - Public static getInstance() method to access the single instance
 *
 * WHY SINGLETON?
 * 1. Ensures only one instance manages all campus data
 * 2. Centralized control of students, clubs, and events
 * 3. Prevents data inconsistency from multiple instances
 * 4. Provides global point of access
 *
 * Responsibilities (MVC Controller):
 * 1. Handle business logic
 * 2. Coordinate between models and views
 * 3. Validate data
 * 4. Manage data persistence
 */
public class CampusService {

    // SINGLETON IMPLEMENTATION
    private static CampusService instance;

    /**
     * SINGLETON: Private constructor to prevent external instantiation
     */
    private CampusService() {
        this.students = new HashMap<>();
        this.clubs = new HashMap<>();
        this.events = new HashMap<>();
        this.fileManager = new FileManager();
        System.out.println("✅ CampusService Singleton instance created");
    }

    /**
     * SINGLETON: Static method to get the single instance
     * @return The single instance of CampusService
     */
    public static CampusService getInstance() {
        if (instance == null) {
            instance = new CampusService();
        }
        return instance;
    }

    // Data storage using HashMap for O(1) access
    private Map<Integer, Student> students;
    private Map<Integer, Club> clubs;
    private Map<Integer, Event> events;
    private FileManager fileManager;

    // Configuration constants
    private static final int MAX_CLUBS_PER_STUDENT = 5;
    private static final int MAX_EVENTS_PER_STUDENT = 10;
    private static final int MAX_MEMBERS_PER_CLUB = 30;
    private static final int MAX_PARTICIPANTS_PER_EVENT = 50;

    // =============== STUDENT OPERATIONS ===============

    /**
     * Adds a new student to the system
     * Uses ObjectFactory (Factory Pattern) to create Student object
     *
     * @param id Student ID (must be unique)
     * @param name Student name
     * @throws ValidationException if validation fails
     */
    public void addStudent(int id, String name) throws ValidationException {
        validateId(id, "Student");
        validateName(name, "Student");

        if (students.containsKey(id)) {
            throw new ValidationException("Student ID " + id + " already exists");
        }

        // Using Factory Pattern to create Student object
        Student student = ObjectFactory.createStudent(id, name);
        students.put(id, student);
    }

    /**
     * Gets a student by ID
     * @param id Student ID
     * @return Student object
     * @throws ValidationException if student not found
     */
    public Student getStudent(int id) throws ValidationException {
        Student student = students.get(id);
        if (student == null) {
            throw new ValidationException("Student with ID " + id + " not found");
        }
        return student;
    }

    // =============== CLUB OPERATIONS ===============

    /**
     * Creates a new club
     * Uses ObjectFactory (Factory Pattern) to create Club object
     *
     * @param id Club ID (must be unique)
     * @param name Club name
     * @param description Club description
     * @throws ValidationException if validation fails
     */
    public void createClub(int id, String name, String description) throws ValidationException {
        validateId(id, "Club");
        validateName(name, "Club");

        if (clubs.containsKey(id)) {
            throw new ValidationException("Club ID " + id + " already exists");
        }

        // Using Factory Pattern to create Club object
        Club club = ObjectFactory.createClub(id, name, description);
        clubs.put(id, club);
    }

    /**
     * Gets a club by ID
     * @param id Club ID
     * @return Club object
     * @throws ValidationException if club not found
     */
    public Club getClub(int id) throws ValidationException {
        Club club = clubs.get(id);
        if (club == null) {
            throw new ValidationException("Club with ID " + id + " not found");
        }
        return club;
    }

    // =============== EVENT OPERATIONS ===============

    /**
     * Creates a new event
     * Uses ObjectFactory (Factory Pattern) to create Event object
     *
     * @param id Event ID (must be unique)
     * @param name Event name
     * @param description Event description
     * @param date Event date (YYYY-MM-DD)
     * @param time Event time (HH:MM)
     * @param clubId Hosting club ID
     * @throws ValidationException if validation fails
     */
    public void createEvent(int id, String name, String description,
                            String date, String time, int clubId) throws ValidationException {
        validateId(id, "Event");
        validateName(name, "Event");
        validateDate(date);
        validateTime(time);

        if (events.containsKey(id)) {
            throw new ValidationException("Event ID " + id + " already exists");
        }

        Club club = getClub(clubId);

        // Using Factory Pattern to create Event object
        Event event = ObjectFactory.createEvent(id, name, description, date, time, clubId);
        events.put(id, event);

        // Add event to club's hosted events
        club.addEvent(id);
    }

    /**
     * Gets an event by ID
     * @param id Event ID
     * @return Event object
     * @throws ValidationException if event not found
     */
    public Event getEvent(int id) throws ValidationException {
        Event event = events.get(id);
        if (event == null) {
            throw new ValidationException("Event with ID " + id + " not found");
        }
        return event;
    }

    // =============== JOIN OPERATIONS ===============

    /**
     * Student joins a club
     * @param studentId Student ID
     * @param clubId Club ID
     * @return Success message
     * @throws ValidationException if operation fails
     */
    public String joinClub(int studentId, int clubId) throws ValidationException {
        Student student = getStudent(studentId);
        Club club = getClub(clubId);

        // Check limits
        if (student.getClubIds().size() >= MAX_CLUBS_PER_STUDENT) {
            throw new ValidationException("Student can only join " + MAX_CLUBS_PER_STUDENT + " clubs");
        }

        if (club.getMemberIds().size() >= MAX_MEMBERS_PER_CLUB) {
            throw new ValidationException("Club can only have " + MAX_MEMBERS_PER_CLUB + " members");
        }

        // Check if already joined
        if (student.isInClub(clubId)) {
            throw new ValidationException("Student is already a member of this club");
        }

        // Update both sides
        student.addClub(clubId);
        club.addMember(studentId);

        return student.getName() + " joined " + club.getName();
    }

    /**
     * Student registers for an event
     * @param studentId Student ID
     * @param eventId Event ID
     * @return Success message
     * @throws ValidationException if operation fails
     */
    public String registerForEvent(int studentId, int eventId) throws ValidationException {
        Student student = getStudent(studentId);
        Event event = getEvent(eventId);

        // Check limits
        if (student.getEventIds().size() >= MAX_EVENTS_PER_STUDENT) {
            throw new ValidationException("Student can only register for " + MAX_EVENTS_PER_STUDENT + " events");
        }

        if (event.getParticipantIds().size() >= MAX_PARTICIPANTS_PER_EVENT) {
            throw new ValidationException("Event can only have " + MAX_PARTICIPANTS_PER_EVENT + " participants");
        }

        // Check if already registered
        if (student.isInEvent(eventId)) {
            throw new ValidationException("Student is already registered for this event");
        }

        // Update both sides
        student.addEvent(eventId);
        event.addParticipant(studentId);

        return student.getName() + " registered for " + event.getName();
    }

    // =============== GET ALL METHODS ===============

    /**
     * Gets all students in the system
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }

    /**
     * Gets all clubs in the system
     * @return List of all clubs
     */
    public List<Club> getAllClubs() {
        return new ArrayList<>(clubs.values());
    }

    /**
     * Gets all events in the system
     * @return List of all events
     */
    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    // =============== FIND METHODS ===============

    /**
     * Finds a student by ID (returns null if not found)
     * @param id Student ID
     * @return Student or null
     */
    public Student findStudent(int id) {
        return students.get(id);
    }

    /**
     * Finds a club by ID (returns null if not found)
     * @param id Club ID
     * @return Club or null
     */
    public Club findClub(int id) {
        return clubs.get(id);
    }

    /**
     * Finds an event by ID (returns null if not found)
     * @param id Event ID
     * @return Event or null
     */
    public Event findEvent(int id) {
        return events.get(id);
    }

    // =============== COUNT METHODS ===============

    public int getStudentCount() { return students.size(); }
    public int getClubCount() { return clubs.size(); }
    public int getEventCount() { return events.size(); }

    // =============== VALIDATION METHODS ===============

    private void validateId(int id, String type) throws ValidationException {
        if (id <= 0) {
            throw new ValidationException(type + " ID must be positive");
        }
    }

    private void validateName(String name, String type) throws ValidationException {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException(type + " name cannot be empty");
        }
        if (name.trim().length() > 50) {
            throw new ValidationException(type + " name cannot exceed 50 characters");
        }
    }

    private void validateDate(String date) throws ValidationException {
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new ValidationException("Date must be in YYYY-MM-DD format");
        }
    }

    private void validateTime(String time) throws ValidationException {
        if (time == null || !time.matches("\\d{2}:\\d{2}")) {
            throw new ValidationException("Time must be in HH:MM format (24-hour)");
        }
    }

    // =============== FILE OPERATIONS ===============

    /**
     * Saves all data to JSON files
     */
    public void saveAllData() {
        try {
            fileManager.saveStudents(new ArrayList<>(students.values()));
            fileManager.saveClubs(new ArrayList<>(clubs.values()));
            fileManager.saveEvents(new ArrayList<>(events.values()));
        } catch (Exception e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    /**
     * Loads all data from JSON files
     * Loads sample data if files don't exist
     */
    public void loadAllData() {
        try {
            // Try to load from files
            loadFromFiles();
            System.out.println("✅ Data loaded from files successfully");
        } catch (Exception e) {
            // Load sample data if files don't exist
            System.out.println("⚠ No data files found. Loading sample data...");
            loadSampleData();
        }
    }

    private void loadFromFiles() throws Exception {
        // Clear current data
        students.clear();
        clubs.clear();
        events.clear();

        // Load from files
        List<Student> loadedStudents = fileManager.loadStudents();
        for (Student student : loadedStudents) {
            students.put(student.getId(), student);
        }

        List<Club> loadedClubs = fileManager.loadClubs();
        for (Club club : loadedClubs) {
            clubs.put(club.getId(), club);
        }

        List<Event> loadedEvents = fileManager.loadEvents();
        for (Event event : loadedEvents) {
            events.put(event.getId(), event);
        }
    }

    private void loadSampleData() {
        try {
            // Load sample students
            addStudent(1001, "Alice Johnson");
            addStudent(1002, "Bob Smith");
            addStudent(1003, "Carol Davis");
            addStudent(1004, "David Wilson");
            addStudent(1005, "Eva Brown");

            // Load sample clubs
            createClub(101, "Computer Science Club", "Programming and technology enthusiasts");
            createClub(102, "Debate Society", "Public speaking and critical thinking skills");
            createClub(103, "Music Club", "For music lovers to practice and perform");

            // Load sample events
            createEvent(10001, "Hackathon 2024", "24-hour coding competition",
                    "2024-11-15", "09:00", 101);
            createEvent(10002, "Public Speaking Workshop", "Learn effective communication",
                    "2024-11-20", "14:00", 102);
            createEvent(10003, "Music Concert", "Annual music concert",
                    "2024-12-10", "18:00", 103);

            // Setup relationships
            joinClub(1001, 101);
            joinClub(1002, 101);
            joinClub(1003, 102);
            joinClub(1004, 101);
            joinClub(1004, 102);

            registerForEvent(1001, 10001);
            registerForEvent(1002, 10001);
            registerForEvent(1003, 10002);
            registerForEvent(1004, 10001);

        } catch (ValidationException e) {
            System.out.println("❌ Error loading sample data: " + e.getMessage());
        }
    }
}
