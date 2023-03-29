package com.uttara.phone.ioServices;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;

public class MySqlContactReaderTest {
    
    MySqlContactReader mySqlContactReader;
    MySqlService mySqlService = new MySqlService();
    ContactBean contactBean, contactBean3;
    MySqlContactWriter mWriter;
    MySqlContactDeleter mDeleter;

    @BeforeEach
    void init() {
        mySqlContactReader = new MySqlContactReader();
        mWriter = new MySqlContactWriter();
        mDeleter = new MySqlContactDeleter();
        String phoneBookName = "South Army";
        mySqlService.createContactBook(phoneBookName);
        contactBean = new ContactBean(
            "South Army", 
            new Name(Gender.M, "Arulmozhi Varman", "Arul"), List.of("9532142666", "4266695321"), 
            "No 95, Viharam road, Anuradhapuram, Srilanka - 321579", 
            List.of("lake","asia" , "chola"), 
            List.of("Arulmozhi.Varman@gmail.com", "Varman.Arulmozhi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1525, 2, 14).atStartOfDay()));
        contactBean3 = new ContactBean(
            "South Army", 
            new Name(Gender.M, "Vallavan Vandiyathevan", "Vandi"), List.of("1429532666", "5324266691"), 
            "No 49, Beach palace road, Naagaipattinam, India - 503205", 
            List.of("army","asia" ,"prince", "chola"), 
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1622, 2, 14).atStartOfDay()));    
        mWriter.insertIntoContactsTable(contactBean);
        mWriter.insertIntoContactsTable(contactBean3);
    }

    @Test 
    void testRead() {
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertTrue(mySqlService.contactExists(contactBean));
        assertTrue(mySqlService.contactExists(contactBean3));
        mySqlContactReader.read(contactBean.phoneBookName());

    }
    
    @Disabled("private method")
    @Test
    void testExtractAllData() {
        mySqlContactReader.getAllData(contactBean.phoneBookName()).forEach(list -> System.out.println(list + "\n"));
       
    }

    @Disabled("private method")
    @Test
    void testExtractNameFromAllData() {
        mySqlContactReader.extractNames(mySqlContactReader.getAllData(contactBean.phoneBookName()));
    }

    @Disabled("private method")
    @Test
    void testExtractPhoneNumbers() {
        mySqlContactReader.extractPhoneNumbers("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Disabled("private method")
    @Test
    void testExtractEmailIds() {
        mySqlContactReader.extractEmailIds("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Disabled("private method")
    @Test
    void testExtractAddress() {
        mySqlContactReader.extractAddress("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Disabled("private method")
    @Test
    void testExtractTags() {
        mySqlContactReader.extractTags("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Disabled("private method")
    @Test
    void testDates() {
        mySqlContactReader.extractDates("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @AfterEach
    void clean() {
        mDeleter.deleteFromContactsTable(contactBean.name().getFullName());
        mDeleter.deleteFromContactsTable(contactBean3.name().getFullName());
        mySqlService.deleteContactBook(contactBean.phoneBookName());
    }


}
