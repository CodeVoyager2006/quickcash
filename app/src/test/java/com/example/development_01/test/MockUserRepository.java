package com.example.development_01.test;

import com.example.development_01.core.data.firebase.IUserRepository;

/**
 * Mock repository for Robolectric tests
 * Does NOT initialize Firebase - safe for unit tests
 */
public class MockUserRepository implements IUserRepository {

    // Empty constructor
    public MockUserRepository() {
    }

    @Override
    public void registerUser(String userName, String email, String password, String role, RegistrationCallback callback) {
        // Do NOTHING - Robolectric tests only test validation
    }
}