package com.example.development_01;

import android.content.Context;
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
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import com.example.development_01.LoginActivity; // <-- change if your Activity is elsewhere

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class LoginRobolectric {

    ActivityController<LoginActivity> controller;
    LoginActivity shadow;
    Context context;

    @Before
    public void setup() {
        controller = Robolectric.buildActivity(LoginActivity.class).setup();
        shadow = controller.get();
        context = ApplicationProvider.getApplicationContext();
    }

    // 1. valid password + invalid email => "Invalid Credential" in snackbar
    @Test
    public void validPasswordInvalidEmail_showsInvalidCredentialSnackbar() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("invalidEmail"); // invalid email

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("Password123@"); // valid password

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert getSnackbarText().equals("Invalid Credential");
    }

    // 2. valid password + valid email => "Valid Credential" in a text view
    @Test
    public void validPasswordValidEmail_showsValidCredentialTextView_case1() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("test1@gmail.com");

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("Password123@");

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert existsTextViewWithText("Valid Credential");
    }

    // 3. valid password + empty email => "Enter detail" in snackbar
    @Test
    public void validPasswordEmptyEmail_showsEnterDetailSnackbar() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("");

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("Password123@");

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert getSnackbarText().equals("Enter detail");
    }

    // 4. invalid password + valid email => "Invalid Credential" in snackbar
    @Test
    public void invalidPasswordValidEmail_showsInvalidCredentialSnackbar() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("test1@gmail.com");

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("wrong"); // invalid password

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert getSnackbarText().equals("Invalid Credential");
    }

    // 5. valid password + valid email => "Valid Credential" in a text view (duplicate requirement)
    @Test
    public void validPasswordValidEmail_showsValidCredentialTextView_case2() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("test1@gmail.com");

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("Password123@");

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert existsTextViewWithText("Valid Credential");
    }

    // 6. empty password + valid email => "Enter detail" in snackbar
    @Test
    public void emptyPasswordValidEmail_showsEnterDetailSnackbar() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("test1@gmail.com");

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("");

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert getSnackbarText().equals("Enter detail");
    }

    // 7. empty password + empty email => "Enter detail" in snackbar
    @Test
    public void emptyPasswordEmptyEmail_showsEnterDetailSnackbar() {
        EditText email = shadow.findViewById(com.example.development_01.R.id.emailAddress);
        email.setText("");

        EditText password = shadow.findViewById(com.example.development_01.R.id.userPassword);
        password.setText("");

        Button loginBtn = shadow.findViewById(com.example.development_01.R.id.loginBtn);
        loginBtn.performClick();

        assert getSnackbarText().equals("Enter detail");
    }

    // -------- helpers (kept minimal, aligned with the example's style) --------

    private String getSnackbarText() {
        View decor = shadow.getWindow().getDecorView();
        TextView tv = decor.findViewById(R.id.snackbar_text);
        if (tv == null || tv.getText() == null) {
            return "";
        }
        return tv.getText().toString();
    }

    private boolean existsTextViewWithText(String expected) {
        View root = shadow.findViewById(android.R.id.content);
        if (root == null) return false;
        return findTextView(root, expected) != null;
    }

    private TextView findTextView(View v, String expected) {
        if (v instanceof TextView) {
            CharSequence t = ((TextView) v).getText();
            if (t != null && expected.contentEquals(t)) return (TextView) v;
        }
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                TextView found = findTextView(vg.getChildAt(i), expected);
                if (found != null) return found;
            }
        }
        return null;
    }
}