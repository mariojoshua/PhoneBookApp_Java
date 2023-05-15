package com.uttara.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PhoneHelperTest {
    @Test
    void testChoiceInputandValidation() {

    }

    @Test
    void testCloseResources() {

    }

    @Test
    void testGetUserNumberInput() {
        // tested for 13, 15 "ghee"/, ""," " no exceptions arose
    }

    @Test
    void testGetUserStringInput() {

    }

    @Test
    void testResourceCloser() {

    }

    @Test
    void testValidateCity() {

    }

    @Test
    void testValidateCountry() {

    }

    @Test
    void testValidateDate() {

    }

    @Test
    void testValidateEmail() {

    }

    @Test
    void testValidateHomeNumber() {

    }

    @Test
    void testValidateName() {

    }

    @Test
    void testValidatePhoneNumber() {

    }

    @Test
    void testValidatePincode() {

    }

    @Test
    void testValidateState() {

    }

    @Test
    void testValidateStreetAddress() {

    }

    @Test
    void testValidateTags() {

    }

    @Test 
    void testValidateGender() {
        assertEquals(Constants.SUCCESS, PhoneHelper.validateGender("m"));
        assertEquals(Constants.SUCCESS, PhoneHelper.validateGender("F"));
        assertEquals(Constants.SUCCESS, PhoneHelper.validateGender(" o"));
        assertEquals("Kindly enter only m, f or o as gender", PhoneHelper.validateGender(" ma "));
    }
}
