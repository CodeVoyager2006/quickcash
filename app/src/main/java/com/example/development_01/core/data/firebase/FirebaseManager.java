package com.example.development_01.core.data.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    private static FirebaseManager instance;
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;

    private FirebaseManager() {
        this.auth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
    }

    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseDatabase getDatabase() {
        return database.getReference("users").getDatabase();
    }
}
