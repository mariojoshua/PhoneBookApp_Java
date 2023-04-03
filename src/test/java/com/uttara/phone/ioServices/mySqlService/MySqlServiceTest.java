package com.uttara.phone.ioServices.mySqlService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;


public class MySqlServiceTest {

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
            "North Army",
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
            List.of("Aditha.Karikalan@ymail.com", "Karikalan_Adi@hotmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1622, 2, 14).atStartOfDay()));    
        contactBean4 = new ContactBean(
            "Spy group", new Name(Gender.M, "Azhwarkadiyan Nambi", "Thirumalai"), 
            List.of("2149532666", "6954266321"), 
            "No 105, fort road, Tanjavur, India - 500231", 
            List.of("secret service","asia", "chola"), 
            List.of("Thirumalai.Nambi@yahoo.com", "Azhwarkadiyan_Nambi@gmail.com"), 
            Map.of("dateOfBirth",
            LocalDate.of(1915, 6, 28).atStartOfDay()));    
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
        assertTrue (mySqlService.createContactBook(contactBean.phoneBookName()));
        assertTrue (mySqlService.createContactBook(contactBean2.phoneBookName()));
        assertTrue (mySqlService.writeContacts(contactBean));
        assertTrue (mySqlService.writeContacts(contactBean2));
        assertTrue(mySqlService.contactExists(contactBean));
        assertTrue(mySqlService.contactExists(contactBean2));
        assertFalse (mySqlService.writeContacts(contactBean));
        assertFalse (mySqlService.writeContacts(contactBean2));
        assertTrue(mySqlService.deleteContact(contactBean.phoneBookName(), contactBean.name().getFullName()));
        assertTrue(mySqlService.deleteContact(contactBean2.phoneBookName(), contactBean2.name().getFullName()));
        assertFalse(mySqlService.contactExists(contactBean));
        assertTrue (mySqlService.deleteContactBook(contactBean.phoneBookName()));
        assertTrue (mySqlService.deleteContactBook(contactBean2.phoneBookName()));
    }

    @Test
    public void testContactExists() {
        assertFalse (mySqlService.contactExists(contactBean2));
        assertTrue (mySqlService.createContactBook(contactBean2.phoneBookName()));
        assertTrue (mySqlService.writeContacts(contactBean2));
        assertTrue (mySqlService.contactExists(contactBean2));
        assertFalse (mySqlService.writeContacts(contactBean2));
        assertTrue (mySqlService.deleteContact(contactBean2.phoneBookName(), contactBean2.name().getFullName()));
        assertTrue (mySqlService.deleteContactBook(contactBean2.phoneBookName()));
    }

    @Test
    public void testDeleteContact() {
        assertTrue (mySqlService.createContactBook(contactBean3.phoneBookName()));
        assertTrue (mySqlService.createContactBook(contactBean4.phoneBookName()));
        assertTrue (mySqlService.writeContacts(contactBean3));
        assertTrue (mySqlService.writeContacts(contactBean4));
        assertTrue(mySqlService.contactExists(contactBean3));
        assertTrue(mySqlService.contactExists(contactBean4));
        assertFalse (mySqlService.writeContacts(contactBean3));
        assertFalse (mySqlService.writeContacts(contactBean4));
        assertTrue(mySqlService.deleteContact(contactBean3.phoneBookName(), contactBean3.name().getFullName()));
        assertTrue(mySqlService.deleteContact(contactBean4.phoneBookName(), contactBean4.name().getFullName()));
        assertFalse(mySqlService.contactExists(contactBean3));
        assertFalse(mySqlService.contactExists(contactBean4));
        assertTrue (mySqlService.deleteContactBook(contactBean3.phoneBookName()));
        assertTrue (mySqlService.deleteContactBook(contactBean4.phoneBookName()));
    }

   
    @Test
    public void testReadContact() {
        assertTrue (mySqlService.createContactBook(contactBean.phoneBookName()));
        assertTrue (mySqlService.writeContacts(contactBean));
        assertTrue (mySqlService.contactExists(contactBean));
        assertTrue (mySqlService.writeContacts(contactBean3));
        assertTrue (mySqlService.contactExists(contactBean3));
        
        List<ContactBean> expectedList = List.of(contactBean, contactBean3);
        List<ContactBean> actualList = mySqlService.readContact(contactBean.phoneBookName());
        assertEquals(expectedList.size(),actualList.size());

        ContactBean expectedContactBean = expectedList.get(0);
        Optional<ContactBean> result  = actualList.stream()
                    .filter(obj -> obj.name().getFullName().equals(expectedContactBean.name().getFullName()))
                    .findFirst();
        ContactBean actualContactBean = result.isPresent() ? result.get(): null ;
        
        // checks if full name is same
        String expectedFullName = expectedContactBean.name().getFullName();
        String actualFullName = actualContactBean.name().getFullName();
        assertEquals(expectedFullName, actualFullName);
        
        // checks if phone numbers are same
        List<String> expectedNumbers = expectedContactBean.phoneNumbers();
        List<String> actualNumbers = new LinkedList<>(actualContactBean.phoneNumbers());
        actualNumbers.sort((p1, p2) -> p2.compareTo(p1));
        assertEquals(expectedNumbers, actualNumbers);

        //check if tags are same
        List<String> expectedTags = expectedContactBean.tags();
        List<String> actualTags = new LinkedList<>(actualContactBean.tags());
        actualTags.sort((tag1, tag2) -> tag1.compareTo(tag2));
        assertEquals(expectedTags, actualTags);    
 
        // checks if address is same
        String expectedAddress = expectedList.get(1).address();
        boolean actualAddressMatch = actualList.stream()
                                    .anyMatch(obj -> obj.address().equals(expectedAddress));
        assertTrue(actualAddressMatch);    

        assertTrue(mySqlService.deleteContact(contactBean.phoneBookName(), contactBean.name().getFullName()));
        assertTrue(mySqlService.deleteContact(contactBean3.phoneBookName(), contactBean3.name().getFullName()));
        assertTrue (mySqlService.deleteContactBook(contactBean3.phoneBookName()));
    }

    @Test
    public void testUpdateContacts() {
        //fail("Not yet Implemented");
    }

    @Test
    void testGetTags_ID() {
        // mySqlService.getTags_IDFromContacts_TagsTable(contacts_ID);
    }

    @Test
    void testgetPhonebook_ID() {
        // mySqlService.getTags_IDFromContacts_TagsTable(contacts_ID);
    }

    @Test
    void testgetTags_IDFromContacts_TagsTable() {
        // mySqlService.getTags_IDFromContacts_TagsTable(contacts_ID);
    }

    @Test
    void testcheckIfTagExistsInTagsTable() {
        // mySqlService.getTags_IDFromContacts_TagsTable(contacts_ID);
    }


}
