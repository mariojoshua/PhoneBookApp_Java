package com.uttara.phone.ioServices.mySqlService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
         mySqlService.createContactBook(contactBean.phoneBookName());
         mWriter.write(contactBean);
         mWriter.write(contactBean3);
    }

    @Test 
    void testRead() {
        assertTrue(mySqlService.contactBookExists(contactBean.phoneBookName()));
        assertTrue(mySqlService.contactExists(contactBean));
        assertTrue(mySqlService.contactExists(contactBean3));
        assertEquals(2,mySqlContactReader.read(contactBean.phoneBookName()).size());

    }
    
    @Test
    void testExtractAllData() {
        assertEquals(28, mySqlContactReader.getAllData(contactBean.phoneBookName()).size());
    }

    @Test
    void testExtractNameFromAllData() {
        Name expected = contactBean.name();
        List<HashMap<String,Object>> data = mySqlContactReader.getAllData(contactBean.phoneBookName());
        Optional<Name> nameFromDatabase =  mySqlContactReader.extractNames(data).stream()
                                .filter(name -> name.equals(expected))
                                .findFirst();
        Name actual = nameFromDatabase.isPresent() ? nameFromDatabase.get(): new Name();
        assertEquals(expected,actual);
    }

    @Test
    void testExtractPhoneNumbers() {
        List<String>expectedList = contactBean3.phoneNumbers();
        String fullName = contactBean3.name().getFullName();
        List<HashMap<String,Object>> data = mySqlContactReader.getAllData(contactBean3.phoneBookName())
                                                .stream()
                                                .filter(obj -> obj.get("fullname").equals(fullName))
                                                .sorted((obj1, obj2) -> ((String)obj1.get("phoneNumber")).compareTo((String)obj2.get("phoneNumber")))
                                                .collect(Collectors.toUnmodifiableList());
        List<String> actualList =  mySqlContactReader.extractPhoneNumbers(fullName, data);
        assertEquals(expectedList, actualList);                               
    }

    @Test
    void testExtractEmailIds() {
        mySqlContactReader.extractEmailIds("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Test
    void testExtractAddress() {
        mySqlContactReader.extractAddress("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Test
    void testExtractTags() {
        mySqlContactReader.extractTags("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @Test
    void testDates() {
        mySqlContactReader.extractDates("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
    }

    @AfterEach
    void clean() {
        mDeleter.delete(contactBean.name().getFullName());
        mDeleter.delete(contactBean3.name().getFullName());
        mySqlService.deleteContactBook(contactBean.phoneBookName());
    }


}
