package com.uttara.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(true, mySqlService.createContactBook("cats"));
        assertEquals(false, mySqlService.createContactBook("cats"));
    }

    @Test
    public void testContactBookExists() {        
        assertTrue(mySqlService.contactBookExists("friends"));
    }

    @Test
    public void testContactExists() {

    }



    @Test
    public void testDeleteContact() {

    }

    @Test
    public void testDeleteContactBook() {

    }

    @Test
    public void testInsertIntoContactsTagsLinkTable() {

    }

    @Test
    public void testReadContact() {

    }

    @Test
    public void testUpdateContacts() {

    }

    @Test
    public void testWriteContacts() {

    }
}
