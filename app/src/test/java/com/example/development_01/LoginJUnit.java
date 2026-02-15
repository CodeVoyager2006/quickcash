package com.example.development_01;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.development_01.core.CredentialValidator;

public class LoginJUnit {

    private CredentialValidator validator;       // <-- rename to your class
    private FakeAuthRepository fakeDb;       // controlled “database”

    @Before
    public void setUp() {
        FakeAuthRepository db = fakeDb;
        db = new FakeAuthRepository();
        // Seed a known record in the “database”
        db.put("test1@gmail.com", "Password123@");

        // Create validator with fake repository
        validator = new CredentialValidator(db);  // <-- adjust constructor to match your project
    }

    // ---------------- nullEmail ----------------

    @Test
    public void nullEmail_returnsTrue_whenNull() {
        assertTrue(validator.nullEmail(null));
    }

    @Test
    public void nullEmail_returnsTrue_whenEmpty() {
        assertTrue(validator.nullEmail(""));
    }

    @Test
    public void nullEmail_returnsFalse_whenNonEmpty() {
        assertFalse(validator.nullEmail("test1@gmail.com"));
    }

    // ---------------- evaluateEmail ----------------

    @Test
    public void evaluateEmail_returnsTrue_whenEmailExistsInDb() {
        assertTrue(validator.evaluateEmail("test1@gmail.com"));
    }

    @Test
    public void evaluateEmail_returnsFalse_whenEmailNotInDb() {
        assertFalse(validator.evaluateEmail("missing@gmail.com"));
    }

    @Test
    public void evaluateEmail_returnsFalse_whenNullOrEmpty() {
        assertFalse(validator.evaluateEmail(null));
        assertFalse(validator.evaluateEmail(""));
    }

    // ---------------- nullPassword ----------------

    @Test
    public void nullPassword_returnsTrue_whenNull() {
        assertTrue(validator.nullPassword(null));
    }

    @Test
    public void nullPassword_returnsTrue_whenEmpty() {
        assertTrue(validator.nullPassword(""));
    }

    @Test
    public void nullPassword_returnsFalse_whenNonEmpty() {
        assertFalse(validator.nullPassword("Password123@"));
    }

    // ---------------- evaluatePassword ----------------

    @Test
    public void evaluatePassword_returnsTrue_whenEmailExistsAndPasswordMatches() {
        assertTrue(validator.evaluatePassword("test1@gmail.com", "Password123@"));
    }

    @Test
    public void evaluatePassword_returnsFalse_whenEmailExistsButPasswordDoesNotMatch() {
        assertFalse(validator.evaluatePassword("test1@gmail.com", "WrongPass"));
    }

    @Test
    public void evaluatePassword_returnsFalse_whenEmailDoesNotExist() {
        assertFalse(validator.evaluatePassword("missing@gmail.com", "Password123@"));
    }

    @Test
    public void evaluatePassword_returnsFalse_whenEmailOrPasswordNullOrEmpty() {
        assertFalse(validator.evaluatePassword(null, "Password123@"));
        assertFalse(validator.evaluatePassword("", "Password123@"));
        assertFalse(validator.evaluatePassword("test1@gmail.com", null));
        assertFalse(validator.evaluatePassword("test1@gmail.com", ""));
    }

    // --------------------------------------------------------------------
    // Fake repository and small interface for testability.
    // If your project already has a repository/service, adapt these types to match it.
    // --------------------------------------------------------------------

    public interface AuthRepository {
        boolean emailExists(String email);
        String getPasswordForEmail(String email);
    }

    public static class FakeAuthRepository implements AuthRepository {
        private final java.util.Map<String, String> store = new java.util.HashMap<>();

        public void put(String email, String password) {
            store.put(email, password);
        }

        @Override
        public boolean emailExists(String email) {
            return email != null && store.containsKey(email);
        }

        @Override
        public String getPasswordForEmail(String email) {
            return store.get(email);
        }
    }

    /**
     * Example implementation shell so the test compiles if you keep it here.
     * In your project, DELETE this inner class and import your real class instead.
     */
    public static class CredentialValidator {
        private final AuthRepository repo;

        public CredentialValidator(AuthRepository repo) {
            this.repo = repo;
        }

        public boolean nullEmail(String email) {
            return email == null || email.trim().isEmpty();
        }

        public boolean evaluateEmail(String email) {
            if (nullEmail(email)) return false;
            return repo.emailExists(email);
        }

        public boolean nullPassword(String password) {
            return password == null || password.trim().isEmpty();
        }

        public boolean evaluatePassword(String email, String password) {
            if (nullEmail(email) || nullPassword(password)) return false;
            if (!repo.emailExists(email)) return false;

            String stored = repo.getPasswordForEmail(email);
            return password.equals(stored);
        }
    }
}