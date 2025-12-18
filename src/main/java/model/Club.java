package model;

import java.util.*;

/**
 * Club Model Class
 *
 * Represents a club entity in the system.
 * This is part of the MVC MODEL layer - responsible for data structure.
 *
 * Attributes:
 * - id: Unique identifier for the club
 * - name: Club name
 * - description: Club description
 * - memberIds: List of student IDs who are club members
 * - eventIds: List of event IDs hosted by the club
 */
public class Club {
    private int id;
    private String name;
    private String description;
    private List<Integer> memberIds;
    private List<Integer> eventIds;

    /**
     * Default constructor (required for JSON serialization)
     */
    public Club() {
        this.memberIds = new ArrayList<>();
        this.eventIds = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     * @param id Club ID
     * @param name Club name
     * @param description Club description
     */
    public Club(int id, String name, String description) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Integer> getMemberIds() { return memberIds; }
    public void setMemberIds(List<Integer> memberIds) { this.memberIds = memberIds; }

    public List<Integer> getEventIds() { return eventIds; }
    public void setEventIds(List<Integer> eventIds) { this.eventIds = eventIds; }

    /**
     * Adds a member to the club
     * @param studentId ID of student to add as member
     */
    public void addMember(int studentId) {
        if (!memberIds.contains(studentId)) {
            memberIds.add(studentId);
        }
    }

    /**
     * Adds an event to the club's hosted events
     * @param eventId ID of event to add
     */
    public void addEvent(int eventId) {
        if (!eventIds.contains(eventId)) {
            eventIds.add(eventId);
        }
    }

    /**
     * Checks if a student is a member of this club
     * @param studentId Student ID to check
     * @return true if student is member, false otherwise
     */
    public boolean hasMember(int studentId) {
        return memberIds.contains(studentId);
    }

    @Override
    public String toString() {
        return "Club{id=" + id + ", name='" + name + "', members=" +
                memberIds.size() + ", events=" + eventIds.size() + "}";
    }
}
