package com.example.development_01;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ForgotPWUiTest {

    private static final long TIMEOUT_MS = 10_000;

    private UiDevice device;
    private String targetPackage;


    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        targetPackage = context.getPackageName();

        // Launch app from home screen (cold start)
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(targetPackage);
        assertNotNull("Launch intent was null. Check your targetPackage/app install.", intent);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for app to appear
        device.wait(Until.hasObject(By.pkg(targetPackage).depth(0)), TIMEOUT_MS);
    }

    /**
     * 1: Find the button with the label "Forgot Password"
     * 2: Click the "Forgot Password" button and get move to a new screen
     * 3: The new screen will have 2 input fields with the labels: "Email" and "New Password",
     *    and 1 button with label "Submit"
     * 4: Enter a valid email and a valid password then click "Submit"
     * 5: A snackbar will show up saying "Reset Successful"
     * 6: The screen is changed back into the login screen.
     */
    @Test
    public void ableToReset() {
        UiObject2 resetbutton = device.wait(Until.findObject(By.text("Forgot Password")), TIMEOUT_MS);
        assertNotNull("Could not find button with text: Forgot Password", resetbutton);
        resetbutton.click();

        UiObject2 emailInput = findInputFieldForLabel("Email");
        assertNotNull("Could not find an input field for label: Email", emailInput);
        emailInput.setText("test1@gmail.com");


        UiObject2 passwordInput = findInputFieldForLabel("New Password");
        assertNotNull("Could not find an input field for label: Password", passwordInput);
        passwordInput.setText("Password123@");

        UiObject2 submitBtn = device.wait(Until.findObject(By.text("Submit")), TIMEOUT_MS);
        assertNotNull("Could not find button with text: Submit", submitBtn);
        submitBtn.click();

        // Test to see if we can get the snackbar
        boolean snackbarShown = device.wait(
                Until.hasObject(By.text("Reset Successful")),
                TIMEOUT_MS);
        assertTrue("Snackbar 'Reset Successful' did not appear", snackbarShown);

        // Test to see if we're on the login page again
        UiObject2 forgotBtnAgain = device.wait(
                Until.findObject(By.text("Forgot Password")),
                TIMEOUT_MS);
        assertNotNull("Returned to login screen failed – 'Forgot Password' button not found",
                forgotBtnAgain);

        UiObject2 li_emailInput = findInputFieldForLabel("Email");
        assertNotNull("Could not find an input field for label: Email", li_emailInput);
        li_emailInput.setText("test1@gmail.com");


        UiObject2 li_passwordInput = findInputFieldForLabel("Password");
        assertNotNull("Could not find an input field for label: Password", li_passwordInput);
        li_passwordInput.setText("Password123@");


        UiObject2 loginButton = device.wait(Until.findObject(By.text("Log in")), TIMEOUT_MS);
        assertNotNull("Could not find button with text: Log In", loginButton);
        loginButton.click();

        // Pass condition: text view with "Log In Successful"
        boolean successShown =
                device.wait(Until.hasObject(By.text("Valid Credential")), TIMEOUT_MS);

        assertTrue("Expected success text was not shown: Valid Credential", successShown);
    }

    /**
     * Attempts to find the EditText associated with a label, such as a Material TextInputLayout label.
     * Works best when the visible label text is exactly "Email"/"Password".
     */
    private UiObject2 findInputFieldForLabel(String labelText) {
        // Fast path: if the hint is set directly on the EditText
        UiObject2 byHint = device.findObject(By.hint(labelText));
        if (byHint != null) return byHint;

        // Find the label TextView (e.g., Material floating label)
        UiObject2 label = device.findObject(By.text(labelText));
        if (label == null) return null;

        // Walk up a few parent levels and search for an EditText within that container
        UiObject2 parent = label.getParent();
        for (int i = 0; i < 4 && parent != null; i++) {
            UiObject2 editText = parent.findObject(By.clazz("android.widget.EditText"));
            if (editText != null) return editText;
            parent = parent.getParent();
        }

        return null;
    }
}
