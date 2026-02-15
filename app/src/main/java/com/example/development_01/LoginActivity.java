package com.example.development_01;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.example.development_01.core.CredentialValidator;
import com.example.development_01.data.databaseCRUD;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView statusTextView;
    private CredentialValidator validator;
    private databaseCRUD database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components (matches Robolectric test IDs)
        emailEditText = findViewById(R.id.emailAddress);
        passwordEditText = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginBtn);
        statusTextView = findViewById(R.id.statusTextView); // For "Valid Credential"

        // Initialize dependencies (TDD pattern from tests)
        this.database = new com.example.development_01.data.databaseCRUD();
        this.validator = new CredentialValidator(this.database);

        Log.d("LoginActivity", "onCreate finished");

        setupLoginButton();
    }

    protected void setupLoginButton() {
        loginButton.setOnClickListener(this::handleLoginClick);
    }

    protected void handleLoginClick(View view) {
        String email = getEmail().trim();
        String password = getPassword().trim();

        // Test case 3,6,7: Empty fields check
        if (validator.nullEmail(email) || validator.nullPassword(password)) {
            showErrorSnackbar(view, "Enter detail");
            return;
        }

        // Test case 1,4: Invalid credentials check
        if (!validator.evaluateEmail(email) || !validator.evaluatePassword(email, password)) {
            showErrorSnackbar(view, "Invalid Credential");
            return;
        }

        // Session persistence (AC4)
        MainActivity.setLoggedIn(this, true);
        // TODO: Launch next activity after login success
        // For now satisfies UI tests

        // Test case 2,5: Valid credentials success
        showSuccessMessage();
    }

    protected String getEmail() {
        return emailEditText.getText().toString();
    }

    protected String getPassword() {
        return passwordEditText.getText().toString();
    }

    protected void showErrorSnackbar(View view, String message) {
        // Matches Robolectric getSnackbarText() helper
        View root = findViewById(android.R.id.content);
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    protected void showSuccessMessage() {
        // Matches existsTextViewWithText("Valid Credential")
        if (statusTextView != null) {
            statusTextView.setText("Valid Credential");
            statusTextView.setVisibility(View.VISIBLE);
        }
    }
}
