package util;

import model.*;
import service.CampusService;
import java.util.*;

/**
 * Report Generator Class
 *
 * Generates formatted reports for students, clubs, and events.
 * Includes statistics and detailed information.
 */
public class ReportGenerator {

    /**
     * Generates a comprehensive student report
     * @param service CampusService instance
     */
    public static void generateStudentReport(CampusService service) {
        List<Student> students = service.getAllStudents();

        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("               📊 STUDENT REPORT");
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println("Total Students: " + students.size());

        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        int totalClubs = 0;
        int totalEvents = 0;

        for (Student student : students) {
            System.out.println("\n👤 " + student.getName() + " (ID: " + student.getId() + ")");

            // Club memberships
            List<Integer> clubIds = student.getClubIds();
            if (!clubIds.isEmpty()) {
                System.out.println("  🏢 Club Memberships (" + clubIds.size() + "):");
                for (int clubId : clubIds) {
                    Club club = service.findClub(clubId);
                    if (club != null) {
                        System.out.println("    • " + club.getName());
                    }
                }
            } else {
                System.out.println("  🏢 No club memberships");
            }

            // Event registrations
            List<Integer> eventIds = student.getEventIds();
            if (!eventIds.isEmpty()) {
                System.out.println("  🎉 Event Registrations (" + eventIds.size() + "):");
                for (int eventId : eventIds) {
                    Event event = service.findEvent(eventId);
                    if (event != null) {
                        System.out.println("    • " + event.getName() + " (" + event.getDate() + ")");
                    }
                }
            } else {
                System.out.println("  🎉 No event registrations");
            }

            totalClubs += clubIds.size();
            totalEvents += eventIds.size();
        }

        // Statistics
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("📈 STATISTICS:");
        System.out.println("  👥 Total Students: " + students.size());
        System.out.println("  🤝 Total Club Memberships: " + totalClubs);
        System.out.println("  📝 Total Event Registrations: " + totalEvents);

        if (!students.isEmpty()) {
            double avgClubs = (double) totalClubs / students.size();
            double avgEvents = (double) totalEvents / students.size();
            System.out.printf("  📊 Average Clubs per Student: %.2f%n", avgClubs);
            System.out.printf("  📊 Average Events per Student: %.2f%n", avgEvents);
        }
        System.out.println("══════════════════════════════════════════════════════");
    }

    /**
     * Generates a comprehensive club report
     * @param service CampusService instance
     */
    public static void generateClubReport(CampusService service) {
        List<Club> clubs = service.getAllClubs();

        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("               📋 CLUB REPORT");
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println("Total Clubs: " + clubs.size());

        if (clubs.isEmpty()) {
            System.out.println("No clubs in the system.");
            return;
        }

        int totalMembers = 0;
        int totalEvents = 0;

        for (Club club : clubs) {
            System.out.println("\n🏢 " + club.getName() + " (ID: " + club.getId() + ")");
            System.out.println("  📝 Description: " + club.getDescription());

            // Members
            List<Integer> memberIds = club.getMemberIds();
            if (!memberIds.isEmpty()) {
                System.out.println("  👥 Members (" + memberIds.size() + "):");
                for (int memberId : memberIds) {
                    Student student = service.findStudent(memberId);
                    if (student != null) {
                        System.out.println("    • " + student.getName() + " (ID: " + student.getId() + ")");
                    }
                }
            } else {
                System.out.println("  👥 No members");
            }

            // Hosted events
            List<Integer> eventIds = club.getEventIds();
            if (!eventIds.isEmpty()) {
                System.out.println("  🎉 Hosted Events (" + eventIds.size() + "):");
                for (int eventId : eventIds) {
                    Event event = service.findEvent(eventId);
                    if (event != null) {
                        System.out.println("    • " + event.getName() + " (" + event.getDate() + ")");
                    }
                }
            } else {
                System.out.println("  🎉 No hosted events");
            }

            totalMembers += memberIds.size();
            totalEvents += eventIds.size();
        }

        // Statistics
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("📈 STATISTICS:");
        System.out.println("  🏢 Total Clubs: " + clubs.size());
        System.out.println("  👥 Total Members: " + totalMembers);
        System.out.println("  🎉 Total Events Hosted: " + totalEvents);

        if (!clubs.isEmpty()) {
            double avgMembers = (double) totalMembers / clubs.size();
            double avgEvents = (double) totalEvents / clubs.size();
            System.out.printf("  📊 Average Members per Club: %.2f%n", avgMembers);
            System.out.printf("  📊 Average Events per Club: %.2f%n", avgEvents);
        }
        System.out.println("══════════════════════════════════════════════════════");
    }

    /**
     * Generates a comprehensive event report
     * @param service CampusService instance
     */
    public static void generateEventReport(CampusService service) {
        List<Event> events = service.getAllEvents();

        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("               📈 EVENT REPORT");
        System.out.println("══════════════════════════════════════════════════════");
        System.out.println("Total Events: " + events.size());

        if (events.isEmpty()) {
            System.out.println("No events in the system.");
            return;
        }

        int totalParticipants = 0;

        for (Event event : events) {
            System.out.println("\n🎉 " + event.getName() + " (ID: " + event.getId() + ")");
            System.out.println("  📝 Description: " + event.getDescription());
            System.out.println("  📅 Date/Time: " + event.getDate() + " " + event.getTime());

            // Host club
            Club club = service.findClub(event.getClubId());
            if (club != null) {
                System.out.println("  🏢 Host Club: " + club.getName());
            }

            // Participants
            List<Integer> participantIds = event.getParticipantIds();
            if (!participantIds.isEmpty()) {
                System.out.println("  👥 Participants (" + participantIds.size() + "):");
                for (int participantId : participantIds) {
                    Student student = service.findStudent(participantId);
                    if (student != null) {
                        System.out.println("    • " + student.getName() + " (ID: " + student.getId() + ")");
                    }
                }
            } else {
                System.out.println("  👥 No participants yet");
            }

            totalParticipants += participantIds.size();
        }

        // Statistics
        System.out.println("\n══════════════════════════════════════════════════════");
        System.out.println("📈 STATISTICS:");
        System.out.println("  🎉 Total Events: " + events.size());
        System.out.println("  👥 Total Participants: " + totalParticipants);

        if (!events.isEmpty()) {
            double avgParticipants = (double) totalParticipants / events.size();
            System.out.printf("  📊 Average Participants per Event: %.2f%n", avgParticipants);
        }
        System.out.println("══════════════════════════════════════════════════════");
    }
}