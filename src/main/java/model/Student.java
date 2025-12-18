package model;

import java.util.*;

/**
 * Student Model Class
 *
 * Represents a student entity in the system.
 * This is part of the MVC MODEL layer - responsible for data structure.
 *
 * Attributes:
 * - id: Unique identifier for the student
 * - name: Student's full name
 * - clubIds: List of club IDs the student has joined
 * - eventIds: List of event IDs the student has registered for
 */
public class Student {
    private int id;
    private String name;
    private List<Integer> clubIds;
    private List<Integer> eventIds;

    /**
     * Default constructor (required for JSON serialization)
     */
    public Student() {
        this.clubIds = new ArrayList<>();
        this.eventIds = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     * @param id Student ID
     * @param name Student name
     */
    public Student(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Integer> getClubIds() { return clubIds; }
    public void setClubIds(List<Integer> clubIds) { this.clubIds = clubIds; }

    public List<Integer> getEventIds() { return eventIds; }
    public void setEventIds(List<Integer> eventIds) { this.eventIds = eventIds; }

    /**
     * Adds a club to student's club list
     * @param clubId ID of club to add
     */
    public void addClub(int clubId) {
        if (!clubIds.contains(clubId)) {
            clubIds.add(clubId);
        }
    }

    /**
     * Adds an event to student's event list
     * @param eventId ID of event to add
     */
    public void addEvent(int eventId) {
        if (!eventIds.contains(eventId)) {
            eventIds.add(eventId);
        }
    }

    /**
     * Checks if student is in a specific club
     * @param clubId Club ID to check
     * @return true if student is in club, false otherwise
     */
    public boolean isInClub(int clubId) {
        return clubIds.contains(clubId);
    }

    /**
     * Checks if student is registered for a specific event
     * @param eventId Event ID to check
     * @return true if student is registered, false otherwise
     */
    public boolean isInEvent(int eventId) {
        return eventIds.contains(eventId);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', clubs=" +
                clubIds.size() + ", events=" + eventIds.size() + "}";
    }
}