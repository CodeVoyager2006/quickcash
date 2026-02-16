package com.example.development_01.androidTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.development_01";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        Assert.assertNotNull(appIntent);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfSignUpPageIsVisible() {
        UiObject userNameBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/userNameBox"));
        assertTrue(userNameBox.exists());

        UiObject emailAddressBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/emailAddressBox"));
        assertTrue(emailAddressBox.exists());

        UiObject passwordBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/userPasswordBox"));
        assertTrue(passwordBox.exists());

        UiObject employerRoleBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/employerRadioBtn"));
        assertTrue(employerRoleBox.exists());

        UiObject employeeRoleBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/employeeRadioBtn"));
        assertTrue(employeeRoleBox.exists());

        UiObject validateBtn = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/validateBtn"));
        assertTrue(validateBtn.exists());
    }

    @Test
    public void checkIfUserCanTypeInField() throws UiObjectNotFoundException {
        UiObject userNameBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/userNameBox"));
        userNameBox.setText("Iron Man");
        assertEquals("Iron Man", userNameBox.getText());

        UiObject emailAddressBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/emailAddressBox"));
        emailAddressBox.setText("IamIronMan@123.com");
        assertEquals("IamIronMan@123.com", emailAddressBox.getText());
    }

    @Test
    public void checkIfUserCanSelectRole() throws UiObjectNotFoundException {
        UiObject employerRoleBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/employerRadioBtn"));
        employerRoleBox.click();
        assertTrue(employerRoleBox.isChecked());

        UiObject employeeRoleBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/employeeRadioBtn"));
        employeeRoleBox.click();
        assertTrue(employeeRoleBox.isChecked());

        // Check if Employer is unchecked
        assertFalse(employerRoleBox.isChecked());
    }

    @Test
    public void checkIfPasswordToggleWorks() throws Exception {
        UiObject passwordBox = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/userPasswordBox"));
        passwordBox.setText("TestPassword123");
        UiObject toggleIcon = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/text_input_end_icon"));

        assertTrue("Password toggle should exist", toggleIcon.exists());
        assertTrue("Password toggle should be clickable", toggleIcon.isClickable());

        toggleIcon.click();
        toggleIcon.click();
    }

    @Test
    public void checkIfSignUpButtonIsClickable() throws UiObjectNotFoundException {
        UiObject validateBtn = device.findObject(new UiSelector().resourceId(launcherPackage + ":id/validateBtn"));
        assertTrue(validateBtn.isClickable());
        validateBtn.click();
    }
}
