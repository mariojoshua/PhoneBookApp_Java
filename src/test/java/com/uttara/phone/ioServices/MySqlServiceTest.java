package com.uttara.phone.ioServices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.management.ConstructorParameters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;
import com.uttara.phone.ioServices.MySqlService;


public class MySqlServiceTest {

    MySqlService mySqlService;

    @BeforeEach
    public void init() {
        mySqlService = new MySqlService();
    }

    @Test
    public void testCreateContactBook() {
        assertTrue (mySqlService.createContactBook("park11"));
       assertTrue(mySqlService.contactBookExists("park11"));
        //assertFalse (mySqlService.createContactBook("park11"));
        assertTrue (mySqlService.deleteContactBook("park11"));
        assertFalse(mySqlService.contactBookExists("park11"));
    }

    @Test
    public void testContactBookExists() { 
        assertFalse(mySqlService.contactBookExists("navy12"));  
        assertTrue(mySqlService.createContactBook("navy12"));     
        assertTrue(mySqlService.contactBookExists("navy12"));
        assertTrue(mySqlService.deleteContactBook("navy12"));
        assertFalse(mySqlService.contactBookExists("navy12"));
    }

    @Test
    public void testDeleteContactBook() {
        assertTrue (mySqlService.createContactBook("school14"));
        assertTrue(mySqlService.contactBookExists("school14"));
        assertTrue (mySqlService.deleteContactBook("school14"));
        assertFalse(mySqlService.contactBookExists("school14"));
        assertFalse (mySqlService.deleteContactBook("school14"));
    }

    @Test
    public void testWriteContacts() {
        String phoneBookName = "lake cleaning group";
        ContactBean contactBean = new ContactBean(
            phoneBookName, 
            new Name(Gender.M, "Arulmozhi Varman", "Arul"), List.of("9532142666", "4266695321"), 
            "No 95, Viharam road, Anuradhapuram, Srilanka - 321579", 
            List.of("lake","asia" , "chola"), 
            List.of("Arulmozhi.Varman@gmail.com", "Varman.Arulmozhi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1525, 2, 14)));
        assertTrue (mySqlService.createContactBook(phoneBookName));
        assertTrue (mySqlService.writeContacts(contactBean));
        // assertTrue (mySqlService.contactExists("arulmozhi varman", "lake cleaning group"));
        // assertFalse (mySqlService.writeContacts(contactBean, phoneBookName));
        // assertEquals(List.of(contactBean),mySqlService.readContact(phoneBookName));
        // assertTrue (mySqlService.deleteContact("Arulmozhi.Varman@gmail.com", phoneBookName));
        assertTrue (mySqlService.deleteContactBook(phoneBookName));
    }

    @Test
    public void testContactExists() {
        ContactBean contactBean = new ContactBean(
            "North Army",
            new Name(Gender.M, "Aditha Karikalan", "Adi"), 
            List.of("1429532666", "5324266691"), 
            "No 66, Golden palace road, Kanchipuram, India - 503125", 
            List.of("army","asia" , "chola"), 
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1922, 2, 14)));
        assertTrue (mySqlService.createContactBook(contactBean.phoneBookName()));
        //assertTrue (mySqlService.writeContacts(contactBean));
        assertTrue (mySqlService.contactExists(contactBean));
        //assertFalse (mySqlService.writeContacts(contactBean));
        //assertEquals(List.of(contactBean),mySqlService.readContact(contactBean.phoneBookName()));
        //assertTrue (mySqlService.deleteContact("Arulmozhi.Varman@gmail.com", contactBean.phoneBookName()));
    }

    @Test
    public void testDeleteContact() {
        String phoneBookName = "spy group";
        ContactBean contactBean = new ContactBean(
            phoneBookName, new Name(Gender.M, "Azhwarkadiyan Nambi", "Thirumalai"), 
            List.of("2149532666", "6954266321"), 
            "No 105, fort road, Tanjavur, India - 500231", 
            List.of("secret service","asia", "chola"), 
            List.of("Thirumalai.Nambi@yahoo.com", "Azhwarkadiyan_Nambi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1915, 6, 28)));
        assertTrue (mySqlService.createContactBook(phoneBookName));
        assertTrue (mySqlService.writeContacts(contactBean));
        assertTrue (mySqlService.contactExists( new ContactBean("lake cleaning group", new Name("Nambi"))));
        assertFalse (mySqlService.writeContacts(contactBean));
        // assertEquals(List.of(contactBean),mySqlService.readContact(phoneBookName));
        // assertTrue (mySqlService.deleteContact("Arulmozhi.Varman@gmail.com", phoneBookName));
    }


   
    @Test
    public void testReadContact() {
        String phoneBookName = "South Army";
        ContactBean contactBean = new ContactBean(
            phoneBookName, 
            new Name(Gender.M, "Arulmozhi Varman", "Arul"), List.of("9532142666", "4266695321"), 
            "No 95, Viharam road, Anuradhapuram, Srilanka - 321579", 
            List.of("army","asia", "chola"), 
            List.of("Arulmozhi.Varman@gmail.com", "Varman.Arulmozhi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1723, 2, 14)));
        assertTrue (mySqlService.createContactBook(phoneBookName));
        assertTrue (mySqlService.writeContacts(contactBean));
        assertTrue (mySqlService.contactExists(contactBean));
        
        contactBean = new ContactBean(
            phoneBookName, 
            new Name(Gender.M, "Vallavan Vandiyathevan", "Vandi"), List.of("1429532666", "5324266691"), 
            "No 49, Beach palace road, Naagaipattinam, India - 503205", 
            List.of("army","asia" ,"prince", "chola"), 
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1622, 2, 14)));
        ContactBean contactBean1 = new ContactBean("lake cleaning group",new Name("Aditha"));    
        assertTrue (mySqlService.createContactBook(phoneBookName));
        assertTrue (mySqlService.writeContacts(contactBean));
        assertFalse (mySqlService.contactExists(contactBean));
        assertTrue (mySqlService.contactExists(contactBean1 ));
        assertFalse (mySqlService.writeContacts(contactBean));
        assertFalse (mySqlService.writeContacts(contactBean));
        assertTrue (mySqlService.contactExists(contactBean1));
        // assertEquals(List.of(contactBean),mySqlService.readContact(phoneBookName));
        // assertTrue (mySqlService.deleteContact("Arulmozhi.Varman@gmail.com", phoneBookName));
    }

    @Test
    public void testUpdateContacts() {
        fail("Not yet Implemented");
    }


}
