package com.example.development_01.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;


import com.example.development_01.R;
import com.example.development_01.core.ui.MainActivity;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class RobolectricTest {

    ActivityController<MainActivity> controller;
    MainActivity shadow;
    Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        controller = Robolectric.buildActivity(MainActivity.class);

        shadow = controller.get();
        shadow.setUserRepository(new MockUserRepository());

        controller.create().start().resume();
    }

    @Test
    public void checkIfUserNameIsEmpty() {
        EditText emailAddressBox = shadow.findViewById(R.id.emailAddressBox);
        emailAddressBox.setText("IamIronMan@123.com");

        EditText userPasswordBox = shadow.findViewById(R.id.userPasswordBox);
        userPasswordBox.setText("abc@123");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals(context.getString(R.string.EMPTY_USERNAME), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfEmailAddressIsEmpty() {
        EditText userNameBox = shadow.findViewById(R.id.userNameBox);
        userNameBox.setText("Iron man");

        EditText userPasswordBox = shadow.findViewById(R.id.userPasswordBox);
        userPasswordBox.setText("abc@123");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals(context.getString(R.string.EMPTY_EMAIL), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfEmailAddressIsValid() {
        EditText userNameBox = shadow.findViewById(R.id.userNameBox);
        userNameBox.setText("Iron man");

        EditText emailAddressBox = shadow.findViewById(R.id.emailAddressBox);
        emailAddressBox.setText("IamIronMan@123.com");

        EditText userPasswordBox = shadow.findViewById(R.id.userPasswordBox);
        userPasswordBox.setText("abc@123");
        RadioButton employerRadioBtn = shadow.findViewById(R.id.employerRadioBtn);
        employerRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals("Registering...", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfEmailAddressIsInvalid() {
        EditText userNameBox = shadow.findViewById(R.id.userNameBox);
        userNameBox.setText("Iron man");

        EditText emailAddressBox = shadow.findViewById(R.id.emailAddressBox);
        emailAddressBox.setText("IamIronMan123.com");

        EditText userPasswordBox = shadow.findViewById(R.id.userPasswordBox);
        userPasswordBox.setText("abc@123");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals(context.getString(R.string.INVALID_EMAIL), ShadowToast.getTextOfLatestToast());
    }



    @Test
    public void checkIfPasswordIsStrong() {
        EditText userName =shadow.findViewById(R.id.userNameBox);
        userName.setText("Iron man");

        EditText emailAddress = shadow.findViewById(R.id.emailAddressBox);
        emailAddress.setText("IamIronMan@123.com");

        EditText userPassword = shadow.findViewById(R.id.userPasswordBox);
        userPassword.setText("abc@123");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals("Registering...", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        EditText userName =shadow.findViewById(R.id.userNameBox);
        userName.setText("Iron man");

        EditText emailAddress = shadow.findViewById(R.id.emailAddressBox);
        emailAddress.setText("IamIronMan@123.com");

        EditText userPassword = shadow.findViewById(R.id.userPasswordBox);
        userPassword.setText("abc12");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals(context.getString(R.string.INVALID_PASSWORD), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfPasswordIsEmpty() {
        EditText userName =shadow.findViewById(R.id.userNameBox);
        userName.setText("Iron man");

        EditText emailAddress = shadow.findViewById(R.id.emailAddressBox);
        emailAddress.setText("IamIronMan@123.com");

        EditText userPassword = shadow.findViewById(R.id.userPasswordBox);
        userPassword.setText("");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals(context.getString(R.string.EMPTY_PASSWORD), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfRoleIsEmpty() {
        EditText userName =shadow.findViewById(R.id.userNameBox);
        userName.setText("Iron man");

        EditText emailAddress = shadow.findViewById(R.id.emailAddressBox);
        emailAddress.setText("IamIronMan@123.com");

        EditText userPassword = shadow.findViewById(R.id.userPasswordBox);
        userPassword.setText("abc@123");

        RadioButton employeeRadioBtn = shadow.findViewById(R.id.employeeRadioBtn);
        employeeRadioBtn.setChecked(false);

        RadioButton employerRadioBtn = shadow.findViewById(R.id.employerRadioBtn);
        employerRadioBtn.setChecked(false);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals(context.getString(R.string.EMPTY_ROLE), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void checkIfValidationPassesWithValidInput() {
        EditText userName = shadow.findViewById(R.id.userNameBox);
        userName.setText("Tony Stark");

        EditText emailAddress = shadow.findViewById(R.id.emailAddressBox);
        emailAddress.setText("tony@stark.com");

        EditText userPassword = shadow.findViewById(R.id.userPasswordBox);
        userPassword.setText("Stark@123");

        RadioButton employerRadioBtn = shadow.findViewById(R.id.employerRadioBtn);
        employerRadioBtn.setChecked(true);

        Button validateBtn = shadow.findViewById(R.id.validateBtn);
        validateBtn.performClick();

        // Verify "Registering..." toast shows (validation passed)
        Toast latestToast = ShadowToast.getLatestToast();
        assertNotNull("Toast should be shown", latestToast);
        assertEquals("Registering...", ShadowToast.getTextOfLatestToast());
    }

}
