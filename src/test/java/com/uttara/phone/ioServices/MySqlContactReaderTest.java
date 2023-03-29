package com.uttara.phone.ioServices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MySqlContactReaderTest {
    
    MySqlContactReader mySqlContactReader;

    @BeforeEach
    void init() {
        mySqlContactReader = new MySqlContactReader();
    }

    @Test 
    void testRead() {
        mySqlContactReader.read("South Army");
        
    }
    
    @Test
    void testExtractAllData() {
        mySqlContactReader.getAllData("South Army").forEach(list -> System.out.println(list + "\n"));
       
    }

    @Test
    void testExtractNameFromAllData() {
        mySqlContactReader.extractNames(mySqlContactReader.getAllData("South Army"));
    }

    @Test
    void testExtractPhoneNumbers() {
        mySqlContactReader.extractPhoneNumbers("Arulmozhi Varman", mySqlContactReader.getAllData("South Army"));
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


}
