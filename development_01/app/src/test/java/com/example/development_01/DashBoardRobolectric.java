package com.example.development_01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.R; // for R.id.snackbar_text

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import com.example.development_01.LoginActivity; // <-- change if your Activity is elsewhere

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class DashBoardRobolectric {

    ActivityController<LoginActivity> controller;
    LoginActivity shadow;
    Context context;

    @Before
    public void setup() {
        controller = Robolectric.buildActivity(LoginActivity.class).setup();
        shadow = controller.get();
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void employeeEmailLaunchesEmployeeActivity(){
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("abd.123@dal.ca");
        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        Intent startedIntent = Shadows.shadowOf(shadow).getNextStartedActivity();
        assertEquals(EmployeeActivity.class.getName(),
                startedIntent.getComponent().getClassName());
    }

    @Test
    public void employerEmail_launchesEmployerActivity(){
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("abdul.123@dal.ca");
        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        Intent startedIntent = Shadows.shadowOf(shadow).getNextStartedActivity();
        assertEquals(EmployerActivity.class.getName(),
                startedIntent.getComponent().getClassName());
    }

    @Test
    public void employeeActivityDisplaysSearchForJobs(){
        EmployeeActivity activity = Robolectric.buildActivity(EmployeeActivity.class).setup().get();
        TextView roleText = activity.findViewById(com.example.development_01.R.id.employee_role);
        assertEquals("Search for Jobs", roleText.getText().toString());
    }

    @Test
    public void employerActivityDisplaysPostForJobs(){
        EmployerActivity activity = Robolectric.buildActivity(EmployerActivity.class).setup().get();
        TextView roleText = activity.findViewById(com.example.development_01.R.id.employer_role);
        assertEquals("Post a Job", roleText.getText().toString());
    }

    @Test
    public void employeeActivityBackButtonDoesNotLeave(){
        EmployeeActivity activity = Robolectric.buildActivity(EmployeeActivity.class).setup().get();
        activity.getOnBackPressedDispatcher().onBackPressed();
        assertFalse(activity.isFinishing());
    }

    @Test
    public void employerActivityBackButtonDoesNotLeave(){
        EmployerActivity activity = Robolectric.buildActivity(EmployerActivity.class).setup().get();
        activity.getOnBackPressedDispatcher().onBackPressed();
        assertFalse(activity.isFinishing());
    }

    @Test
    public void employeeLogoutReturnsToLogin(){
        EmployeeActivity activity = Robolectric.buildActivity(EmployeeActivity.class).setup().get();
        Button logutBtn = activity.findViewById(com.example.development_01.R.id.employeeLogOut);
        logutBtn.performClick();

        Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(LoginActivity.class.getName(), intent.getComponent().getClassName());
        assertTrue(activity.isFinishing());
    }

    @Test
    public void employerLogoutReturnsToLogin(){
        EmployerActivity activity = Robolectric.buildActivity(EmployerActivity.class).setup().get();
        Button logutBtn = activity.findViewById(com.example.development_01.R.id.employerLogOut);
        logutBtn.performClick();

        Intent intent = Shadows.shadowOf(activity).getNextStartedActivity();
        assertEquals(LoginActivity.class.getName(), intent.getComponent().getClassName());
        assertTrue(activity.isFinishing());
    }


}