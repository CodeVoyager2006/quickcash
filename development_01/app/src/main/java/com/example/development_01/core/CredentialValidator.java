package com.example.development_01.core;

import com.example.development_01.data.AuthRepository;

// Class for validating user credentials
public class CredentialValidator{

    private final AuthRepository repo;

    // Constructor with dependency injection (TDD requirement)
    public CredentialValidator(AuthRepository repo) {
        this.repo = repo;
    }

    // Validates if email is null/empty (JUnitTest requirement)
    public boolean nullEmail(String email) {
        return email == null || email.trim().isEmpty();
    }

    // Validates if email exists in database (JUnitTest requirement)
    public boolean evaluateEmail(String email) {
        if (nullEmail(email)) return false;
        return repo.emailExists(email);
    }

    // Validates if password is null/empty (JUnitTest requirement)
    public boolean nullPassword(String password) {
        return password == null || password.trim().isEmpty();
    }

    // Validates email/password combination (JUnitTest requirement)
    public boolean evaluatePassword(String email, String password) {
        if (nullEmail(email) || nullPassword(password)) return false;
        if (!repo.emailExists(email)) return false;

        String storedPassword = repo.getPasswordForEmail(email);
        return password.equals(storedPassword);
    }

    // Existing method from your stub (JUnitTest checkEmailAddressIsCorrect)
    public boolean validEmailAddress(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // If the String matches the regex pattern, return true
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }
}
