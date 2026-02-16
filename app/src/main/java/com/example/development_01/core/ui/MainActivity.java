package com.example.development_01.core.ui;

import static android.widget.Toast.makeText;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.development_01.R;
import com.example.development_01.core.core.CredentialValidator;
import com.example.development_01.core.data.firebase.IUserRepository;
import com.example.development_01.core.data.firebase.UserRepository;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CredentialValidator validator;
    IUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setupRegistrationButton();
        this.validator = new CredentialValidator();

        // Only initialize if not already set
        if (this.userRepository == null) {
            userRepository = new UserRepository();
        }
    }

    public void setUserRepository(IUserRepository repository) {
        this.userRepository = repository;
    }

    protected void setupRegistrationButton() {
        Button registerBtn = findViewById(R.id.validateBtn);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String userName = getUserName();
        String emailAddress = getEmailAddress();
        String password = getPassword();
        String role = getRole();

// Validate input
        String errorMessage = validateInput(emailAddress, password, role);

        if (errorMessage.isEmpty()) {
// Validation passed
            getShadowToast("Registering...").show();
// Register user with Firebase
            registerUserWithFirebase(userName, emailAddress, password, role);
        } else {
// Show error message
            getShadowToast(errorMessage).show();
        }
    }

    /**
     * AC4: Create user in Firebase Auth and save role to Realtime Database
     * AC5: Handle "User already exists" error
     */
    private void registerUserWithFirebase(String userName, String emailAddress, String password, String role) {
        userRepository.registerUser(userName, emailAddress, password, role,
                new IUserRepository.RegistrationCallback() {
                    @Override
                    public void onSuccess(String userId) {
                        //saveToDatabase(emailAddress, role);
                        getShadowToast("Registration successful!").show();

                        // TODO: Navigate to appropriate screen based on role
                        move2WelcomeScreen("Registration successful!", role);
                }
                    @Override
                    public void onError(String error) {
                        // AC5: Display specific error messages
                        if (error.contains("already exists") || error.contains("already in use")) {
                            getShadowToast("User already exists").show();
                        } else {
                            getShadowToast("Registration failed: " + error).show();
                        }
                    }
                });
    }

    protected String getUserName() {
        EditText userNameBox = findViewById(R.id.userNameBox);
        return userNameBox.getText().toString();
    }

    protected String getEmailAddress() {
        EditText emailAddressBox = findViewById(R.id.emailAddressBox);
        return emailAddressBox.getText().toString();
    }

    protected String getPassword() {
        EditText userPasswordBox = findViewById(R.id.userPasswordBox);
        return userPasswordBox.getText().toString();
    }

    protected String getRole() {
        RadioGroup roleGroup = findViewById(R.id.roleRadioGroup);
        int selectedId = roleGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.employerRadioBtn) {
            return "Employer";
        } else if (selectedId == R.id.employeeRadioBtn) {
            return "Employee";
        }
        return "";
    }

    public Toast getShadowToast(String message) {
        return makeText(this, message, Toast.LENGTH_SHORT);
    }
    private String validateInput(String emailAddress, String password, String role) {
        String errorMessage = "";

// Check if email address is valid
        if (emailAddress.trim().isEmpty()) {
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL);
        } else if (!validator.validEmailAddress(emailAddress)) {
            errorMessage = getResources().getString(R.string.INVALID_EMAIL);
        }

// Check if password is valid
        else if (password.trim().isEmpty()) {
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD);
        } else if (!validator.validStrongPassword(password)) {
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD);
        }

// Check if role is selected
        else if (role.trim().isEmpty()) {
            errorMessage = getResources().getString(R.string.EMPTY_ROLE);
        }

// Check if username was entered
        else if (!validator.userNameInput(getUserName())) {
            errorMessage = getResources().getString(R.string.EMPTY_USERNAME);
        }

        return errorMessage;
    }

    protected void saveToDatabase(String emailAddress, String role) {
        // Reserved for additional database operations if needed in the future
        // Primary registration is handled by UserRepository.registerUser()
    }

    protected void move2WelcomeScreen(String message, String role) {
        // TODO: Implement navigation to welcome/home screen based on role
        // Intent intent = new Intent(this, WelcomeActivity.class);
        // intent.putExtra("role", role);
        // startActivity(intent);
        // finish();
    }
}