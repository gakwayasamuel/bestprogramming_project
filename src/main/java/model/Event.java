package model;

import java.util.*;

/**
 * Event Model Class
 *
 * Represents an event entity in the system.
 * This is part of the MVC MODEL layer - responsible for data structure.
 *
 * Attributes:
 * - id: Unique identifier for the event
 * - name: Event name
 * - description: Event description
 * - date: Event date (YYYY-MM-DD)
 * - time: Event time (HH:MM)
 * - clubId: ID of hosting club
 * - participantIds: List of student IDs participating in the event
 */
public class Event {
    private int id;
    private String name;
    private String description;
    private String date;
    private String time;
    private int clubId;
    private List<Integer> participantIds;

    /**
     * Default constructor (required for JSON serialization)
     */
    public Event() {
        this.participantIds = new ArrayList<>();
    }

    /**
     * Parameterized constructor
     * @param id Event ID
     * @param name Event name
     * @param description Event description
     * @param date Event date (YYYY-MM-DD)
     * @param time Event time (HH:MM)
     * @param clubId Hosting club ID
     */
    public Event(int id, String name, String description,
                 String date, String time, int clubId) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.clubId = clubId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getClubId() { return clubId; }
    public void setClubId(int clubId) { this.clubId = clubId; }

    public List<Integer> getParticipantIds() { return participantIds; }
    public void setParticipantIds(List<Integer> participantIds) {
        this.participantIds = participantIds;
    }

    /**
     * Adds a participant to the event
     * @param studentId ID of student to add as participant
     */
    public void addParticipant(int studentId) {
        if (!participantIds.contains(studentId)) {
            participantIds.add(studentId);
        }
    }

    /**
     * Checks if a student is participating in this event
     * @param studentId Student ID to check
     * @return true if student is participant, false otherwise
     */
    public boolean hasParticipant(int studentId) {
        return participantIds.contains(studentId);
    }

    @Override
    public String toString() {
        return "Event{id=" + id + ", name='" + name + "', date='" + date +
                " " + time + "', participants=" + participantIds.size() + "}";
    }
}
