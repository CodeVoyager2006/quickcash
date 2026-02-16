package com.example.development_01.core.data.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseCRUD {
    private final FirebaseAuth auth;
    private final DatabaseReference usersRef;


    public FirebaseCRUD(FirebaseDatabase instance) {
        this.auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        this.usersRef = database.getReference("users");
    }

    /**
     * AC4: Register a new user with the provided email, password and role.
     *
     * @param userName The user's username.
     * @param email    The user's email address.
     * @param password The user's password.
     * @param role     The user's role (either "employer" or "employee").
     */
    public void registerUser(String userName, String email, String password, String role, RegistrationCallback callback) {
        // First check if email exists
        checkEmailExists(email, new EmailCheckCallback() {
            @Override
            public void onResult(boolean exists) {
                if (exists) {
                    callback.onError("User already exists");
                } else {
                    // Create user in Firebase Auth
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Get user ID
                                    String uid = auth.getCurrentUser().getUid();

                                    // Save to database
                                    saveUserToDatabase(uid, userName, email, role, callback);
                                } else {
                                    callback.onError("Registration failed");
                                }
                            });
                }
            }
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }

    /**
     * AC5: Check if an email already exists in the database.
     *
     * @param email The email to check.
     */
    public void checkEmailExists(String email, EmailCheckCallback callback) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean exists = !task.getResult().getSignInMethods().isEmpty();
                        callback.onResult(exists);
                    } else {
                        callback.onError("Error checking email");
                    }
                });
    }

    /**
     * Save user data to Realtime Database
     */
    private void saveUserToDatabase(String uid, String userName, String email, String role, RegistrationCallback callback) {
        DatabaseReference userRef = usersRef.child(uid);

        // Save each field
        userRef.child("userName").setValue(userName);
        userRef.child("email").setValue(email);
        userRef.child("role").setValue(role)
                .addOnSuccessListener(aVoid -> callback.onSuccess(uid))
                .addOnFailureListener(e -> callback.onError("Failed to save user data"));
    }

    /**
     * Get user data from database
     */
    public void getUserData(String uid, UserDataCallback callback) {
        usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userName = snapshot.child("userName").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String role = snapshot.child("role").getValue(String.class);

                    callback.onSuccess(userName, email, role);
                } else {
                    callback.onError("User not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }

    // Callback interfaces
    public interface EmailCheckCallback {
        void onResult(boolean exists);
        void onError(String error);
    }

    public interface RegistrationCallback {
        void onSuccess(String userId);
        void onError(String error);
    }

    public interface UserDataCallback {
        void onSuccess(String userName, String email, String role);
        void onError(String error);
    }
}
