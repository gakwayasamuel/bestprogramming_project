package util;

import model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.*;
import java.util.*;

/**
 * File Manager Class
 *
 * Handles all file operations for JSON data persistence.
 * Stores data in src/data/ directory.
 *
 * Uses Jackson library for JSON serialization/deserialization.
 */
public class FileManager {
    private static final String DATA_DIR = "data/";
    private static final String STUDENTS_FILE = DATA_DIR + "students.json";
    private static final String CLUBS_FILE = DATA_DIR + "clubs.json";
    private static final String EVENTS_FILE = DATA_DIR + "events.json";

    private ObjectMapper objectMapper;

    public FileManager() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Create data directory if it doesn't exist
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    /**
     * Saves students to JSON file
     * @param students List of students to save
     * @throws IOException if file operation fails
     */
    public void saveStudents(List<Student> students) throws IOException {
        objectMapper.writeValue(new File(STUDENTS_FILE), students);
    }

    /**
     * Saves clubs to JSON file
     * @param clubs List of clubs to save
     * @throws IOException if file operation fails
     */
    public void saveClubs(List<Club> clubs) throws IOException {
        objectMapper.writeValue(new File(CLUBS_FILE), clubs);
    }

    /**
     * Saves events to JSON file
     * @param events List of events to save
     * @throws IOException if file operation fails
     */
    public void saveEvents(List<Event> events) throws IOException {
        objectMapper.writeValue(new File(EVENTS_FILE), events);
    }

    /**
     * Loads students from JSON file
     * @return List of students
     * @throws IOException if file doesn't exist or cannot be read
     */
    public List<Student> loadStudents() throws IOException {
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) {
            throw new IOException("Students file not found: " + STUDENTS_FILE);
        }
        return Arrays.asList(objectMapper.readValue(file, Student[].class));
    }

    /**
     * Loads clubs from JSON file
     * @return List of clubs
     * @throws IOException if file doesn't exist or cannot be read
     */
    public List<Club> loadClubs() throws IOException {
        File file = new File(CLUBS_FILE);
        if (!file.exists()) {
            throw new IOException("Clubs file not found: " + CLUBS_FILE);
        }
        return Arrays.asList(objectMapper.readValue(file, Club[].class));
    }

    /**
     * Loads events from JSON file
     * @return List of events
     * @throws IOException if file doesn't exist or cannot be read
     */
    public List<Event> loadEvents() throws IOException {
        File file = new File(EVENTS_FILE);
        if (!file.exists()) {
            throw new IOException("Events file not found: " + EVENTS_FILE);
        }
        return Arrays.asList(objectMapper.readValue(file, Event[].class));
    }
}