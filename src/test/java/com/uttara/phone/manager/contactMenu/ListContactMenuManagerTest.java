package com.uttara.phone.manager.contactMenu;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;
import com.uttara.phone.ioServices.mySqlService.MySqlService;

public class ListContactMenuManagerTest {
    MySqlService mySqlService;
    ContactBean contactBean, contactBean2, contactBean3, contactBean4;
    String phoneBookName;

    @BeforeEach
    public void init() {
        mySqlService = new MySqlService();
        contactBean = new ContactBean( 
            "South Army", 
            new Name(Gender.M, "Arulmozhi Varman", "Arul"), 
            List.of("9532142666", "4266695321"), 
            "No 95, Viharam road, Anuradhapuram, Srilanka - 321579", 
            List.of("asia" , "chola", "lake"), 
            List.of("Arulmozhi.Varman@gmail.com", "Varman.Arulmozhi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1525, 2, 14).atStartOfDay()));
        contactBean2 = new ContactBean(
            "South Army",
            new Name(Gender.M, "Aditha Karikalan", "Adi"), 
            List.of("1429532666", "5324266691"), 
            "No 66, Golden palace road, Kanchipuram, India - 503125", 
            List.of("army","asia" , "chola"), 
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1922, 2, 14).atStartOfDay()));
        contactBean3 = new ContactBean(
            "South Army", 
            new Name(Gender.M, "Vallavan Vandiyathevan", "Vandi"), 
            List.of("1429532666", "5324266691"), 
            "No 49, Beach palace road, Naagaipattinam, India - 503205", 
            List.of("army","asia" ,"prince", "chola"), 
            List.of("Vallavan.Vandiyathevan@ymail.com", "Vandiyathevan_Vallavan@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1622, 2, 14).atStartOfDay()));    
        contactBean4 = new ContactBean(
            "South Army", new Name(Gender.M, "Azhwarkadiyan Nambi", "Thirumalai"), 
            List.of("2149532666", "6954266321"), 
            "No 105, fort road, Tanjavur, India - 500231", 
            List.of("secret service","asia", "chola"), 
            List.of("Thirumalai.Nambi@yahoo.com", "Azhwarkadiyan_Nambi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1915, 6, 28).atStartOfDay()));    
    }

    @Test
    public void testDeleteContact() {
        // assertTrue (mySqlService.createContactBook(contactBean3.phoneBookName()));
        // assertTrue (mySqlService.writeContacts(contactBean));
        // assertTrue (mySqlService.writeContacts(contactBean2));
        // assertTrue(mySqlService.contactExists(contactBean));
        // assertTrue(mySqlService.contactExists(contactBean2));
        // assertTrue (mySqlService.writeContacts(contactBean3));
        // assertTrue (mySqlService.writeContacts(contactBean4));
        // assertTrue(mySqlService.deleteContact(contactBean.phoneBookName(), contactBean.name().getFullName()));
        // assertTrue(mySqlService.deleteContact(contactBean2.phoneBookName(), contactBean2.name().getFullName()));
        // assertTrue(mySqlService.deleteContact(contactBean3.phoneBookName(), contactBean3.name().getFullName()));
        // assertTrue(mySqlService.deleteContact(contactBean4.phoneBookName(), contactBean4.name().getFullName()));
        // assertFalse(mySqlService.contactExists(contactBean3));
        // assertFalse(mySqlService.contactExists(contactBean4));
        // assertTrue (mySqlService.deleteContactBook(contactBean3.phoneBookName()));
    }

}
