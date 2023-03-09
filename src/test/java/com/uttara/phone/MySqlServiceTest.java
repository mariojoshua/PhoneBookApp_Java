package test.java.com.uttara.phone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import main.java.com.uttara.phone.MySqlService;

public class MySqlServiceTest {

    MySqlService mySqlService;

    @Before
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
