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
    void testGetAllData() {
        //mySqlContactReader.getAllData("South Army").forEach(map -> System.out.println(map.toString()));
        // for(Map m: mySqlContactReader.getAllData("South Army")) {
        //     System.out.println(m + "\n");
        // }
        mySqlContactReader.getAllData("South Army").forEach(list -> System.out.println(list + "\n"));
       
    }
}
