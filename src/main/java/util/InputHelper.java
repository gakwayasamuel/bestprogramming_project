package util;

import java.util.Scanner;

/**
 * Input Helper Class
 *
 * Provides utility methods for getting and validating user input.
 * Ensures consistent input handling throughout the application.
 */
public class InputHelper {
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Gets an integer input from user with validation
     * @param prompt Message to display to user
     * @return Valid integer input
     */
    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
            }
        }
    }

    /**
     * Gets an integer input within a specified range
     * @param prompt Message to display to user
     * @param min Minimum allowed value
     * @param max Maximum allowed value
     * @return Valid integer within range
     */
    public static int getInt(String prompt, int min, int max) {
        while (true) {
            int value = getInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("❌ Please enter a number between " + min + " and " + max);
        }
    }

    /**
     * Gets a string input from user
     * @param prompt Message to display to user
     * @return Non-empty string input
     */
    public static String getString(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("❌ Input cannot be empty!");
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        }
        return input;
    }

    /**
     * Gets a date input in YYYY-MM-DD format
     * @param prompt Message to display to user
     * @return Valid date string
     */
    public static String getDate(String prompt) {
        while (true) {
            String date = getString(prompt);
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // Validate year, month, day
                String[] parts = date.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                if (year >= 2023 && year <= 2030 &&
                        month >= 1 && month <= 12 &&
                        day >= 1 && day <= 31) {
                    return date;
                }
            }
            System.out.println("❌ Invalid date! Please use YYYY-MM-DD format (e.g., 2024-12-25)");
        }
    }

    /**
     * Gets a time input in HH:MM format (24-hour)
     * @param prompt Message to display to user
     * @return Valid time string
     */
    public static String getTime(String prompt) {
        while (true) {
            String time = getString(prompt);
            if (time.matches("\\d{2}:\\d{2}")) {
                String[] parts = time.split(":");
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);

                if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
                    return time;
                }
            }
            System.out.println("❌ Invalid time! Please use HH:MM format (24-hour, e.g., 14:30)");
        }
    }

    /**
     * Gets yes/no input from user
     * @param prompt Message to display to user
     * @return true for yes, false for no
     */
    public static boolean getYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("❌ Please enter 'y' for yes or 'n' for no.");
        }
    }
}