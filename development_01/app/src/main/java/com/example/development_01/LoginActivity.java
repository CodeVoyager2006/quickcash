package com.example.development_01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.development_01.data.FirebaseCRUD;
import com.google.android.material.snackbar.Snackbar;
import com.example.development_01.core.CredentialValidator;
import com.example.development_01.data.databaseCRUD;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    FirebaseDatabase database = null;
    FirebaseCRUD crud = null;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView statusTextView;
    private CredentialValidator validator;
    //private databaseCRUD database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize database components
        database = FirebaseDatabase.getInstance();
        crud = new FirebaseCRUD(database);

        // Initialize UI components (matches Robolectric test IDs)
        emailEditText = findViewById(R.id.emailAddress);
        passwordEditText = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginBtn);
        statusTextView = findViewById(R.id.statusTextView);

        validateLogin();

        /*/ Initialize dependencies (TDD pattern from tests)
        this.database = new com.example.development_01.data.databaseCRUD();
        this.validator = new CredentialValidator(this.database);

        //Log.d("LoginActivity", "onCreate finished");*/

        setupLoginButton();
    }

    private void validateLogin() {
        String enteredEmail = emailEditText.getText().toString().trim();
        String enteredPassword = passwordEditText.getText().toString().trim();

        String dbEmail = crud.getExtractedEmailAddress();
        String dbPassword = crud.getExtractedPassword();
        String role = crud.getExtractedRole();

        if (dbEmail == null || dbPassword == null) {
            Toast.makeText(this, "Database not loaded yet", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enteredEmail.equals(dbEmail) && enteredPassword.equals(dbPassword)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

            // TODO: navigate to next screen
            moveToDashboard(role);
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    protected void moveToDashboard(String role){

        if(role.equals("employee")){

            Intent intent = new Intent(this, EmployeeActivity.class);

            intent.putExtra("name", crud.getExtractedName());
            intent.putExtra("email", crud.getExtractedEmailAddress());

            startActivity(intent);
        }
        if(role.equals("employer")){
            Intent intent = new Intent(this, EmployerActivity.class);

            intent.putExtra("name", crud.getExtractedName());
            intent.putExtra("email", crud.getExtractedEmailAddress());

            startActivity(intent);
        }

    }


    protected void setupLoginButton() {
        loginButton.setOnClickListener(
                this::handleLoginClick);
    }

    protected void handleLoginClick(View view) {
        /*String email = getEmail().trim();
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

        // For now satisfies UI tests

        // Test case 2,5: Valid credentials success
        showSuccessMessage();*/

        validateLogin();

        // TODO: Launch next activity after login success



        /*String email = getEmail();

        if(email.equals("abd.123@dal.ca")){
            Intent intent = new Intent(this, EmployeeActivity.class);

            startActivity(intent);
        }

        if(email.equals("abdul.123@dal.ca")){
            Intent intent = new Intent(this, EmployerActivity.class);

            startActivity(intent);
        }*/
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
