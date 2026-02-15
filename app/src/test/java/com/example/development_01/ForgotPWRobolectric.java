package com.example.development_01;

import static org.junit.Assert.assertEquals;

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

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class ForgotPWRobolectric {
    ActivityController<LoginActivity> controller;
    LoginActivity shadow;
    Context context;

    @Before
    public void setup() {
        controller = Robolectric.buildActivity(LoginActivity.class).setup();
        shadow = controller.get();
        context = ApplicationProvider.getApplicationContext();
    }
}
