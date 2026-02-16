package com.example.development_01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.development_01.data.databaseCRUD;
import com.google.firebase.database.FirebaseDatabase;

public class EmployeeActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        this.showEmail();
        this.showName();
        this.setupLogOutButton();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });

    }

    protected void setupLogOutButton() {
        Button logOutButton = findViewById(R.id.employeeLogOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Clear the session preferences (sharedPreferences, Firebase, etc)
                clearUserSession();

                //2. Create intent to go back to login page
                Intent intent = new Intent(EmployeeActivity.this, LoginActivity.class);

                //3. Clear the entire history session aka Clears the back button stack
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                //4. Redirect the user to the logIn page
                startActivity(intent);

                //5. End the current activity
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {}

    protected void showEmail(){
        TextView email = findViewById(R.id.employeeEmail);
        String dbEmail = getIntent().getStringExtra("email");
        email.setText(dbEmail);
    }

    protected void showName(){
        TextView name = findViewById(R.id.employeeName);
        String dbName = getIntent().getStringExtra("name");
        name.setText(dbName);
    }

    //This should clear the Firebase session
    protected void clearUserSession(){
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); //Removes all saved Data
        editor.apply();
    }
}