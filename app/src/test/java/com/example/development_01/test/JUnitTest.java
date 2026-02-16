package com.example.development_01.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.development_01.core.core.CredentialValidator;

import org.junit.Before;
import org.junit.Test;

public class JUnitTest {
    CredentialValidator validator;

    @Before
    public void setup() {
        validator = new CredentialValidator();
    }

    @Test
    public void checkEmailAddressIsCorrect() {
        assertTrue(validator.validEmailAddress("IamIronMan@123.com"));
        assertTrue(validator.validEmailAddress("john.c.calhoun@examplepetstore.com"));
    }

    @Test
    public void checkEmailAddressIsIncorrect() {
        assertFalse(validator.validEmailAddress("Iloveyou3000gmail.com"));
        assertFalse(validator.validEmailAddress("IamIronMan.ca"));
    }

    @Test
    public void checkPasswordIsCorrect() {
        assertTrue(validator.validStrongPassword("abc@123"));
        assertTrue(validator.validStrongPassword("123abc!@#"));
    }

    @Test
    public void checkPasswordIsIncorrect() {
        assertFalse(validator.validStrongPassword("abc12"));
        assertFalse(validator.validStrongPassword("abcd!"));
    }

    @Test
    public void checkRoleIsCorrect(){
        assertTrue(validator.validRole("Employer"));
        assertTrue(validator.validRole("Employee"));
    }

    @Test
    public void checkRoleIsIncorrect(){
        assertFalse(validator.validRole(null));
        assertFalse(validator.validRole(""));
        assertFalse(validator.validRole("Student"));
    }
}
