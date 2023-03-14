package com.uttara.phone.ioServices;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;

public class MySqlContactWriterTest {
    MySqlContactWriter mWriter = null;
    ContactBean contactBean = null;
    MySqlService mySqlService = null;

    @BeforeEach 
    void init() {
        mWriter = new MySqlContactWriter();
        mySqlService = new MySqlService();
        mySqlService.createContactBook("walking group 56");
        contactBean = new ContactBean(
            "walking group 56",
            new Name(Gender.M, "Aditha Karikalan", "Adi"), 
            List.of("1429532666", "5324266691"), 
            "No 66, Golden palace road, Kanchipuram, India - 503125", 
            List.of("army","asia" , "chola"), 
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1922, 2, 14)));
    }

    @Test
    void testGetPhoneBook_ID() {
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertNotEquals(-1,  mWriter.getPhonebook_ID(contactBean));
    }

    @Test
    void testInsertIntoContactsTable() throws SQLException {
        mWriter.insertIntoContactsTable(null);
    }

    @Test
    void testInsertIntoContactsTagsLinkTable() throws SQLException {
        mWriter.insertIntoContactsTagsLinkTable(0, 0);
    }

    @Test
    void testInsertIntoEmailTable() throws SQLException {

    }

    @Test
    void testInsertIntoPhoneNumberTable() throws SQLException {

    }

    @Test
    void testInsertIntoTagsTable() throws SQLException {

    }

    @Test
    void testWrite() throws SQLException {

    }

    @AfterEach  
    void kill() {
        mWriter = new MySqlContactWriter();
        new MySqlService().deleteContactBook("walking group 56");
    }
}