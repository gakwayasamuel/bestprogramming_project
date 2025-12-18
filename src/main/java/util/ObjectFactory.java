package util;

import model.*;

/**
 * Object Factory Class - FACTORY METHOD PATTERN IMPLEMENTATION
 *
 * FACTORY METHOD PATTERN:
 * - Centralized object creation
 * - Hides object creation logic from client code
 * - Provides a simple interface to create objects
 * - Makes code more maintainable and scalable
 *
 * WHY FACTORY PATTERN?
 * 1. Encapsulates object creation logic
 * 2. Easy to add new object types in future
 * 3. Promotes loose coupling
 * 4. Makes testing easier
 * 5. Centralizes object creation rules
 */
public class ObjectFactory {

    /**
     * Factory method to create a Student object
     * @param id Student ID
     * @param name Student name
     * @return New Student object
     */
    public static Student createStudent(int id, String name) {
        return new Student(id, name);
    }

    /**
     * Factory method to create a Club object
     * @param id Club ID
     * @param name Club name
     * @param description Club description
     * @return New Club object
     */
    public static Club createClub(int id, String name, String description) {
        return new Club(id, name, description);
    }

    /**
     * Factory method to create an Event object
     * @param id Event ID
     * @param name Event name
     * @param description Event description
     * @param date Event date (YYYY-MM-DD)
     * @param time Event time (HH:MM)
     * @param clubId Hosting club ID
     * @return New Event object
     */
    public static Event createEvent(int id, String name, String description,
                                    String date, String time, int clubId) {
        return new Event(id, name, description, date, time, clubId);
    }
}