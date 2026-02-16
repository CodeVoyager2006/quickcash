package com.example.development_01.core.data.firebase;

public interface IUserRepository {

    void registerUser(String userName, String email, String password, String role, RegistrationCallback callback);

    interface RegistrationCallback {
        void onSuccess(String userId);
        void onError(String error);
    }

    interface EmailCheckCallback {
        void onResult(boolean exists);
        void onError(String error);
    }
}