package com.uttara.phone.ioServices;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;

public class MySqlContactDeletorTest {
    MySqlContactWriter mWriter = null;
    ContactBean contactBean = null;
    MySqlService mySqlService = null;
    MySqlContactDeleter mDeleter = null;
   
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
    }

    @Test
    void testDelete() {

    }

    @Test
    void testDeleteFromContactsTable() {
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertTrue(mDeleter.deleteFromContactsTable(contactBean.name().getFullName()));
    }

    @Test
    void testDeleteFromContactsTagsLinkTable() {

    }

    @Test
    void testDeleteFromEmailTable() {

    }

    @Test
    void testDeleteFromPhoneNumberTable() {

    }

    @Test
    void testDeleteFromTagsTable() {

    }

    @AfterEach 
    void kill() {
        mySqlService.deleteContactBook(contactBean.phoneBookName());
    }
}
