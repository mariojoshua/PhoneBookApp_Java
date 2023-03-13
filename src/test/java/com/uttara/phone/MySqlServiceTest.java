package com.uttara.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MySqlServiceTest {

    MySqlService mySqlService;

    @BeforeEach
    public void init() {
        mySqlService = new MySqlService();
    }

    @Test
    public void testCreateContactBook() {
        assertTrue (mySqlService.createContactBook("cats"));
       assertTrue(mySqlService.contactBookExists("cats"));
        assertFalse (mySqlService.createContactBook("cats"));
        assertTrue (mySqlService.deleteContactBook("cats"));
        assertFalse(mySqlService.contactBookExists("cats"));
    }

    @Test
    public void testContactBookExists() { 
        assertFalse(mySqlService.contactBookExists("mats"));  
        assertTrue(mySqlService.createContactBook("mats"));     
        assertTrue(mySqlService.contactBookExists("mats"));
        assertTrue(mySqlService.deleteContactBook("mats"));
        assertFalse(mySqlService.contactBookExists("mats"));
    }

    @Test
    public void testDeleteContactBook() {
        assertTrue (mySqlService.createContactBook("hats"));
        assertTrue(mySqlService.contactBookExists("hats"));
        assertTrue (mySqlService.deleteContactBook("hats"));
        assertFalse(mySqlService.contactBookExists("hats"));
        assertFalse (mySqlService.deleteContactBook("hats"));
    }

    @Test
    public void testContactExists() {
        fail("Not yet Implemented");
    }

    @Test
    public void testDeleteContact() {
        fail("Not yet Implemented");
    }

    @Test
    public void testReadContact() {
        fail("Not yet Implemented");
    }

    @Test
    public void testUpdateContacts() {
        fail("Not yet Implemented");
    }

    @Test
    public void testWriteContacts() {
        fail("Not yet Implemented");
    }
}
