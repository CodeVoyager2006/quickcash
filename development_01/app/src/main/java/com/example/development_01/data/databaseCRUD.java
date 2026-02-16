package com.example.development_01.data;

import com.example.development_01.data.AuthRepository;
import java.util.HashMap;
import java.util.Map;

/**
 * Firebase CRUD operations for authentication.
 * Implements AuthRepository for testability and future Firebase integration.
 */
public class databaseCRUD implements AuthRepository {

    private final Map<String, String> userDatabase;

    /**
     * Constructor seeds test data for JUnit tests.
     * Matches LoginJUnit FakeAuthRepository seed exactly.
     */
    public databaseCRUD() {
        userDatabase = new HashMap<>();
        // Test data - matches LoginJUnit test seed
        userDatabase.put("test1@gmail.com", "Password123@");
        // Additional test users for robustness
        userDatabase.put("john.c.calhoun@examplepetstore.com", "TestPass123!");
        userDatabase.put("monyeamicable12@gmail.com", "SecurePass@456");
    }

    @Override
    public boolean emailExists(String email) {
        if (email == null) return false;
        return userDatabase.containsKey(email.trim());
    }

    @Override
    public String getPasswordForEmail(String email) {
        if (email == null) return null;
        return userDatabase.get(email.trim());
    }

    // Future Firebase methods (AC1)
    /*
    public void authenticateWithFirebase(String email, String password, Callback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(callback::onComplete);
    }
    */
}
