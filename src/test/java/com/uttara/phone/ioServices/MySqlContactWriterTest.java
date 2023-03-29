package com.uttara.phone.ioServices;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
    MySqlContactDeleter mDeleter = null;
    int contacts_ID = -1;
    List<Integer> tagIDList = null;
    int tag_ID = -1;

    @BeforeEach 
    void init() {
        mWriter = new MySqlContactWriter();
        mySqlService = new MySqlService();
        mySqlService.createContactBook("walking group 56");
        mDeleter = new MySqlContactDeleter();
        contactBean = new ContactBean(
            "walking group 56",
            new Name(Gender.M, "Aditha Karikalan", "Adi"), 
            List.of("1429532666", "5324266691"), 
            "No 66, Golden palace road, Kanchipuram, India - 503125", 
            List.of("army","asia" , "chola"), 
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1922, 2, 14).atStartOfDay()));  
    }

    @Test
    void testGetPhoneBook_ID() {
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertNotEquals(-1, contacts_ID = mySqlService.getPhonebook_ID(contactBean));
    }

    @Test
    void testInsertIntoContactsTable() {
        assertFalse(mySqlService.contactExists(contactBean));
        assertNotEquals(-1, mWriter.insertIntoContactsTable(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        mDeleter.deleteFromContactsTable(contactBean.name().getFullName());
    }

    @Test
    void testInsertIntoEmailTable() {
        assertNotEquals(-1, mWriter.insertIntoContactsTable(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        assertNotEquals(-1, mWriter.insertIntoEmailTable(contactBean, contacts_ID));
        assertNotEquals(0, mDeleter.deleteFromEmailTable(contacts_ID));
    }

    @Test
    void testInsertIntoPhoneNumberTable() {
        assertNotEquals(-1, mWriter.insertIntoContactsTable(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        assertNotEquals(-1, mWriter.insertIntoPhoneNumberTable(contactBean, contacts_ID));
        assertNotEquals(0, mDeleter.deleteFromPhoneNumberTable(contacts_ID));
    }

    @Test
    void testInsertIntoTagsTable() {
        assertNotEquals(-1, mWriter.insertIntoContactsTable(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        tagIDList = new LinkedList<>();
        assertNotEquals(-1,  tag_ID = mWriter.insertIntoTagsTable(contactBean.tags().get(0), contacts_ID));
        tagIDList.add(tag_ID);
        assertNotEquals(-1,  tag_ID = mWriter.insertIntoTagsTable(contactBean.tags().get(1), contacts_ID));
        tagIDList.add(tag_ID);
        assertNotEquals(-1,  tag_ID = mWriter.insertIntoTagsTable(contactBean.tags().get(2), contacts_ID));
        tagIDList.add(tag_ID);
        assertNotEquals(0, tagIDList.size());
        //assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList));
        assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList.get(0)));
        assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList.get(1)));
        assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList.get(2)));
    }

    @Test
    void testInsertIntoContactsTagsLinkTable() {
        //assertNotEquals(-1,mWriter.insertIntoContactsTagsLinkTable(0, 0));
        assertNotEquals(-1, mWriter.insertIntoContactsTable(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        tagIDList = new LinkedList<>();
        assertNotEquals(-1,  tag_ID = mWriter.insertIntoTagsTable(contactBean.tags().get(0), contacts_ID));
        tagIDList.add(tag_ID);
        assertNotEquals(-1,  tag_ID = mWriter.insertIntoTagsTable(contactBean.tags().get(1), contacts_ID));
        tagIDList.add(tag_ID);
        assertNotEquals(-1,  tag_ID = mWriter.insertIntoTagsTable(contactBean.tags().get(2), contacts_ID));
        tagIDList.add(tag_ID);        
        assertNotEquals(-1, mWriter.insertIntoContactsTagsLinkTable(tagIDList, contacts_ID));
        List<Integer> expected = tagIDList;
        assertIterableEquals(expected, tagIDList = mySqlService.getTags_IDFromContacts_TagsTable(contacts_ID));
        assertNotEquals(-1, mDeleter.deleteFromContactsTagsLinkTable(contacts_ID));
        //assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList));
        assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList.get(0)));
        assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList.get(1)));
        assertNotEquals(-1, mDeleter.deleteFromTagsTable(tagIDList.get(2)));
    }

    @Test
    void testInsertTags() {
        assertNotEquals(-1, mWriter.insertIntoContactsTable(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        assertNotEquals(-1, contacts_ID = mySqlService.getContacts_ID(contactBean.name().getFullName()));
        assertNotEquals(-1, mWriter.insertTags(contactBean, contacts_ID));
        //assertNotEquals(-1, mWriter.insertTags(contactBean, contacts_ID));
        assertNotEquals(0, mySqlService.checkIfTagExistsInTagsTable(contactBean.tags().get(0)));
        assertNotEquals(0, mySqlService.checkIfTagExistsInTagsTable(contactBean.tags().get(1)));
        assertNotEquals(0, mySqlService.checkIfTagExistsInTagsTable(contactBean.tags().get(2)));
        assertNotEquals(-1, mDeleter.deleteTags(contacts_ID));   
    }

    @Test
    void testWrite() {
        assertTrue(mWriter.write(contactBean));
        assertTrue(mySqlService.contactExists(contactBean));
        assertTrue(mDeleter.delete(contactBean.name().getFullName()));
        assertFalse(mySqlService.contactExists(contactBean));
    }

    @AfterEach  
    void kill() {
        /**
         * contacts table has a foreign key to a primary key from contactBook table
         * hence the table containing the foreign key has to be deleted first, 
         * then the independent table
         *  */ 
        mDeleter.deleteFromContactsTable(contactBean.name().getFullName());
        mySqlService.deleteContactBook(contactBean.phoneBookName());
    }
}
