package com.uttara.phone.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import static com.uttara.phone.helper.ValidationHelper.*;
import com.uttara.phone.Constants;

public class ValidationHelperTest {
    @Test
    void testOnlyAlphanumeric() {
        assertEquals(Constants.SUCCESS, onlyAlphanumeric("naman"));
        assertEquals(Constants.SUCCESS, onlyAlphanumeric("home"));
        assertEquals(Constants.SUCCESS, onlyAlphanumeric("SCHOOL"));
        String expected = "Name should only contain alphanumeric characters i.e A-Z, a-z and 0-9";
        assertEquals(expected, onlyAlphanumeric("nam&an"));
        assertEquals(expected, onlyAlphanumeric("Sc*ho=ol"));
        assertEquals(expected, onlyAlphanumeric("#School"));

    }

    @Test
    void testSpecialCharactersPresent() {
        String allowedTokens = "'-";
        assertEquals(Constants.SUCCESS, specialCharactersPresent("MarioD'souza-1", allowedTokens));
        assertEquals(Constants.SUCCESS, specialCharactersPresent("SCHOOL", allowedTokens));
        String expected = "Name should not contain special characters except for " + allowedTokens;
        assertEquals(expected, specialCharactersPresent("nam&an", allowedTokens));
        assertEquals(expected, specialCharactersPresent("Sc*ho=ol", allowedTokens));

    }

    @Test
    void testStartsWithLetter() {
        assertEquals(Constants.SUCCESS, startsWithLetter("SCHOOL"));
        String expected = "Name should start with a letter";
        assertEquals(expected , startsWithLetter("5naman"));
    }

    @Test
    void testValidateAddress() {

    }

    @Test
    void testValidateDate() {
        assertEquals(Constants.SUCCESS, validateDate("01/02/1996"));
        assertEquals(Constants.SUCCESS, validateDate("1/02/1996"));
        assertEquals(Constants.SUCCESS, validateDate("29/02/1996"));
        String expected = "Date could not be parsed, " +
        "kindly enter the date in dd/MM/yyyy format";
        assertEquals(expected , validateDate("5naman"));
        assertEquals(expected , validateDate("02/1996"));
    }

    @Test
    void testValidateEmail() {

    }

    @Test
    void testValidateGender() {
        assertEquals(Constants.SUCCESS, validateGender("m"));
        assertEquals(Constants.SUCCESS, validateGender("F"));
        assertEquals(Constants.SUCCESS, validateGender("o"));
        String expected = "Kindly enter only m, f or o as gender";
        assertEquals(expected, validateGender(" ma "));
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
