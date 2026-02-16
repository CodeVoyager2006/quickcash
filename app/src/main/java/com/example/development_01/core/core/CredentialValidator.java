package com.example.development_01.core.core;

// Class for validating user credentials
public class CredentialValidator {
    // Validates email address
    public boolean validEmailAddress (String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // If the String matches the regex pattern, return true
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}");
    }

    public boolean validStrongPassword (String password) {

        // If the String has at least 6 characters,
        if (password.length() < 6) {
            return false;
        }

        // Include a one number and one symbol return TRUE
        boolean hasDigit = false;
        boolean hasSymbol = false;

        // Check each character in the String
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!(Character.isLetterOrDigit(c))) {
                hasSymbol = true;
            }
        }
            return hasDigit && hasSymbol;
    }

    public boolean userNameInput(String userName) {
        return !userName.isEmpty();
    }

    public boolean roleInput(String role) {
        return !role.isEmpty();
    }

    public boolean validRole(String role) {
        if (role == null) return false;
        String r = role.trim();
        return r.equalsIgnoreCase("Employer") || r.equalsIgnoreCase("Employee");
    }

}
