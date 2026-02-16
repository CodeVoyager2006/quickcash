package com.example.development_01.core.data.firebase;

import com.example.development_01.core.data.firebase.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository implements IUserRepository {
    private final FirebaseAuth auth;
    private final DatabaseReference usersRef;

    public UserRepository() {
        this.auth = FirebaseAuth.getInstance();
        this.usersRef = FirebaseDatabase.getInstance().getReference("users");
    }

    /**
     * AC4: Register a new user with the provided email, password and role.
     *
     * @param userName The user's username.
     * @param email    The user's email address.
     * @param password The user's password.
     * @param role     The user's role (either "employer" or "employee").
     */
    @Override
    public void registerUser(String userName, String email, String password, String role,
                             IUserRepository.RegistrationCallback callback) {
        // First, check if email exists
        checkEmailExists(email, new IUserRepository.EmailCheckCallback() {
            @Override
            public void onResult(boolean exists) {
                if (exists) {
                    callback.onError("User already exists");
                } else {
                    // Create user in Firebase Auth
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    String uid = auth.getCurrentUser().getUid();

                                    // Save user data with role to Realtime Database
                                    User user = new User(userName, email, role);  // Pass parameters!
                                    usersRef.child(uid).setValue(user)
                                            .addOnSuccessListener(aVoid -> callback.onSuccess(uid))
                                            .addOnFailureListener(e -> callback.onError(e.getMessage()));
                                } else {
                                    callback.onError(task.getException() != null ?
                                            task.getException().getMessage() : "Registration failed");
                                }
                            });
                }
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    /**
     * AC5: Check if an email already exists in the database.
     *
     * @param email The email to check.
     */
    public void checkEmailExists(String email, IUserRepository.EmailCheckCallback callback) {
        auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean exists = !task.getResult().getSignInMethods().isEmpty();
                        callback.onResult(exists);
                    } else {
                        callback.onError(task.getException() != null ?
                                task.getException().getMessage() : "Unknown error");
                    }
                });
    }
}