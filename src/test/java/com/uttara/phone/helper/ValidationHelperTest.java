package com.uttara.phone.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import static com.uttara.phone.helper.ValidationHelper.*;
import com.uttara.phone.Constants;

public class ValidationHelperTest {
    @Test
    void testSpecialCharactersPresent() {

    }

    @Test
    void testSpecialCharactersPresent2() {

    }

    @Test
    void testStartsWithLetter() {

    }

    @Test
    void testValidateAddress() {

    }

    @Test
    void testValidateDate() {

    }

    @Test
    void testValidateEmail() {

    }

    @Test
    void testValidateGender() {
        assertEquals(Constants.SUCCESS, validateGender("m"));
        assertEquals(Constants.SUCCESS, validateGender("F"));
        assertEquals(Constants.SUCCESS, validateGender("o"));
        assertEquals("Kindly enter only m, f or o as gender", validateGender(" ma "));
    }

    @Test
    void testValidateHomeNumber() {

    }

        /*1. spl characters allowed',-
        2. start with letter
        3. alphanumeric
        4. spl character not allowed =:
     */
    @Test
    void testValidateName() {
        assertEquals(Constants.SUCCESS, validateName("Mario D'souza - 1"));
        assertEquals(Constants.SUCCESS, validateName(""));
        assertEquals(Constants.SUCCESS, validateName(""));
        assertEquals(Constants.SUCCESS, validateName(""));
    }

    @Test
    void testValidatePhoneNumber() {

    }

    @Test
    void testValidateStreetAddress() {

    }

    @Test
    void testValidateTags() {

    }

    @Test
    void testWhiteSpacesPresent() {
        assertEquals(Constants.SUCCESS, whiteSpacesPresent("naman"));
        assertEquals(Constants.SUCCESS, whiteSpacesPresent("home"));
        assertEquals(Constants.SUCCESS, whiteSpacesPresent("SCHOOL"));
        assertEquals("Name should not have whitespaces!", whiteSpacesPresent("nam an"));
        assertEquals("Name should not have whitespaces!", whiteSpacesPresent("Sc    hool"));
        assertEquals("Name should not have whitespaces!", whiteSpacesPresent("  School"));
    }

    @Test
    void testWordCount() {

    }
}
