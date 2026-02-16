package com.example.development_01;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

@RunWith(AndroidJUnit4.class)
public class DashBoardUITest {
    private UiDevice device;

    @Before
    public  void setup() throws  Exception{
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Context context = ApplicationProvider.getApplicationContext();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage("com.example.development_01");
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(launchIntent);

        device.wait(Until.hasObject(By.pkg("com.example.development_01")), 5000);
    }

    @Test
    public  void employeeLoginShowsSearchForJobs() throws Exception {
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.development_01:id/emailAddress"));
        emailField.setText("abd.123@dal.ca");

        //need to set the password field

        UiObject loginBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/loginBtn"));
        loginBtn.click();

        device.wait(Until.hasObject(By.text("Search for Jobs")), 5000);

        UiObject roleText = device.findObject(new UiSelector().text("Search for Jobs"));
        assertTrue(roleText.exists());
    }

    @Test
    public  void employerLoginShowsPostAJob() throws Exception {
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.development_01:id/emailAddress"));
        emailField.setText("abdul.123@dal.ca");

        //need to set the password field

        UiObject loginBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/loginBtn"));
        loginBtn.click();

        device.wait(Until.hasObject(By.text("Post a Job")), 5000);

        UiObject roleText = device.findObject(new UiSelector().text("Post a Job"));
        assertTrue(roleText.exists());
    }

    @Test
    public void employeeDashboard_backButtonStays() throws Exception{
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.development_01:id/emailAddress"));
        emailField.setText("abd.123@dal.ca");

        //need to set the password field

        UiObject loginBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/loginBtn"));
        loginBtn.click();
        device.wait(Until.hasObject(By.text("Search for Jobs")), 5000);

        device.pressBack();
        UiObject roleText = device.findObject(new UiSelector().text("Search for Jobs"));
        assertTrue(roleText.exists());
        assertFalse(emailField.exists());

    }

    @Test
    public void employerDashboard_backButtonStays() throws Exception{
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.development_01:id/emailAddress"));
        emailField.setText("abdul.123@dal.ca");

        //need to set the password field

        UiObject loginBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/loginBtn"));
        loginBtn.click();
        device.wait(Until.hasObject(By.text("Post a Job")), 5000);

        device.pressBack();
        UiObject roleText = device.findObject(new UiSelector().text("Post a Job"));
        assertTrue(roleText.exists());
        assertFalse(emailField.exists());

    }

    @Test
    public void employeeDashboard_logoutReturnsToLogin() throws Exception{
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.development_01:id/emailAddress"));
        emailField.setText("abd.123@dal.ca");

        //need to set the password field

        UiObject loginBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/loginBtn"));
        loginBtn.click();
        device.wait(Until.hasObject(By.text("Search for Jobs")), 5000);

        UiObject logoutBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/employeeLogOut"));
        logoutBtn.click();

        device.wait(Until.hasObject(By.res("com.example.development_01", "loginBtn")), 5000);

        assertTrue(loginBtn.exists());
    }

    @Test
    public void employerDashboard_logoutReturnsToLogin() throws Exception{
        UiObject emailField = device.findObject(new UiSelector().resourceId("com.example.development_01:id/emailAddress"));
        emailField.setText("abdul.123@dal.ca");

        //need to set the password field

        UiObject loginBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/loginBtn"));
        loginBtn.click();
        device.wait(Until.hasObject(By.text("Post a Job")), 5000);

        UiObject logoutBtn = device.findObject(new UiSelector().resourceId("com.example.development_01:id/employerLogOut"));
        logoutBtn.click();

        device.wait(Until.hasObject(By.res("com.example.development_01", "loginBtn")), 5000);

        assertTrue(loginBtn.exists());
    }
}
