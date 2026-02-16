package com.example.development_01;
import static org.junit.Assert.assertEquals;

import com.example.development_01.core.RoleRouter;
import com.example.development_01.core.CredentialValidator;

import org.junit.Before;
import org.junit.Test;



public class DashBoardJUnit {
    private RoleRouter router;

    @Before
    public void setUp() {
        router = new RoleRouter();
    }
    @Test
    public void testEmployeeEmailReturnsEmployeeRole(){
        assertEquals("Employee", router.determineRole("abd.123@dal.ca"));
    }

    @Test
    public void testEmployerEmailReturnsEmployerRole(){
        assertEquals("Employer", router.determineRole("abdul.123@dal.ca"));
    }

    @Test
    public void testInvalidEmailReturnsInvalidEmail(){
        assertEquals("Invalid Email", router.determineRole("Invalid Email"));
    }

    @Test
    public void testNullEmail(){
        assertEquals("Empty Email", router.determineRole(null));
    }

    @Test
    public void testEmptyEmail(){
        assertEquals("Empty Email", router.determineRole(""));
    }
}
