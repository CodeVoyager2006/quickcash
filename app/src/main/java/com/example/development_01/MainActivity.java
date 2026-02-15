package com.example.development_01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.development_01.R;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private boolean isUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        isUserLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false);

        if (isUserLoggedIn()) {
            launchLoginActivity();
        } else {
            launchLoginActivity();
        }
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void launchNextActivity() {
        // TODO: Launch the main app activity after successful login
        finish();
    }

    // Public static method for LoginActivity to call after successful login
    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    // Getter/Setter for isUserLoggedIn (matches Kotlin property)
    public boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setUserLoggedIn(boolean loggedIn) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, loggedIn);
        editor.apply();
    }
}
