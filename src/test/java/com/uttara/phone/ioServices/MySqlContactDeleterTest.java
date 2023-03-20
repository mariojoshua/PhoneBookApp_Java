package com.uttara.phone.ioServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;

public class MySqlContactDeleterTest {
    MySqlContactWriter mWriter = null;
    ContactBean contactBean = null;
    MySqlService mySqlService = null;
    MySqlContactDeleter mDeleter = null;
    int contacts_ID = -1;
   
    @BeforeEach 
    void init() {
        mWriter = new MySqlContactWriter();
        mySqlService = new MySqlService();
        mDeleter = new MySqlContactDeleter();
        String phoneBookName = "lake cleaning group";
        mySqlService.createContactBook(phoneBookName);
        contactBean = new ContactBean(
            phoneBookName, 
            new Name(Gender.M, "Arulmozhi Varman", "Arul"), List.of("9532142666", "4266695321"), 
            "No 95, Viharam road, Anuradhapuram, Srilanka - 321579", 
            List.of("lake","asia" , "chola"), 
            List.of("Arulmozhi.Varman@gmail.com", "Varman.Arulmozhi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1525, 2, 14)));
            mWriter.insertIntoContactsTable(contactBean);
            contacts_ID = mySqlService.getContacts_ID(phoneBookName);
    }

    @Test
    void testGetContacts_ID() {
        assertNotEquals(-1, mySqlService.getContacts_ID(contactBean.name().getFullName()));
        assertNotEquals(0, mDeleter.deleteFromContactsTable(contactBean.name().getFullName()));
        assertEquals(-1, mySqlService.getContacts_ID(contactBean.name().getFullName()));
    }

    @Test
    void testDelete() {
        
    }

    @Test
    void testDeleteFromContactsTable() {
        assertNotEquals(0, mDeleter.deleteFromContactsTable(contactBean.name().getFullName()));
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertEquals(0, mDeleter.deleteFromContactsTable(contactBean.name().getFullName()));
    }



    @Test
    void testDeleteFromEmailTable() {
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertNotEquals(0, mWriter.insertIntoEmailTable(contactBean, contacts_ID));
        Logger.getInstance().log("contacts_ID = " + contacts_ID);
        assertNotEquals(0, mDeleter.deleteFromEmailTable(contacts_ID));
        assertNotEquals(0, mDeleter.deleteFromContactsTable(contactBean.name().getFullName()));
    }

    @Test
    void testDeleteFromPhoneNumberTable() {      
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        assertNotEquals(0, mWriter.insertIntoPhoneNumberTable(contactBean, contacts_ID));
        assertNotEquals(0, mDeleter.deleteFromPhoneNumberTable(contacts_ID));
        assertEquals(0, mDeleter.deleteFromPhoneNumberTable(contacts_ID));
        assertNotEquals(0, mDeleter.deleteFromContactsTable(contactBean.name().getFullName()));
    }

    
    @Test
    void testGetTags_ID() {
        mySqlService.getTags_ID(contacts_ID);
    }

    @Test
    void testNumberOfContactsLinkedWithTags() {
        mDeleter.numberOfContactsLinkedWithTags(contacts_ID) ;
    }

    @Test
    void testDeleteFromTagsTable() {
        assertEquals(0, mDeleter.deleteFromTagsTable(contacts_ID));
    }
    
    @Test
    void testDeleteFromContactsTagsLinkTable() {
        mDeleter.deleteFromContactsTagsLinkTable(contacts_ID);
    }





    @AfterEach 
    void kill() {
        mDeleter.deleteFromContactsTable(contactBean.name().getFullName());
        mySqlService.deleteContactBook(contactBean.phoneBookName());
    }
}
