
package com.voting.util;

public class InputValidator {

    private InputValidator() {
        // Prevent object creation for this utility class.
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidElectionStatus(String status) {
        return status != null
                && (status.equalsIgnoreCase("OPEN")
                || status.equalsIgnoreCase("CLOSED"));
    }
}
