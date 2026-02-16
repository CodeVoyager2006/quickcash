package com.example.development_01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class EmployerActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        this.showEmail();
        this.showName();

        this.setupLogOutButton();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });
    }

    @Override
    public void onClick(View v) {}

    protected void setupLogOutButton() {
        Button logOutButton = findViewById(R.id.employerLogOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Clear the session preferences (sharedPreferences, Firebase, etc)
                clearUserSession();

                //2. Create intent to go back to login page
                Intent intent = new Intent(EmployerActivity.this, LoginActivity.class);

                //3. Clear the entire history session
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                //4. Start the new Activity
                startActivity(intent);

                //5. End the current activity
                finish();
            }
        });
    }

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