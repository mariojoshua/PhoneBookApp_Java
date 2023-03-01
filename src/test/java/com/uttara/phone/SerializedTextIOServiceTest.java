package test.java.com.uttara.phone;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.plexus.component.collections.ComponentList;
import org.junit.Assert;

import main.java.com.uttara.phone.ContactBean;
import main.java.com.uttara.phone.Name;
import main.java.com.uttara.phone.SerializedTextIOService;
import main.java.com.uttara.phone.Name.Gender;

public class SerializedTextIOServiceTest {
    public static void main(String[] args) {
        SerializedTextIOService sio = new SerializedTextIOService();
        // //List<ContactBean> contactBeanList = new LinkedList<>();
        // Name name = new Name(Pronoun.she_her,"Trisha Krishnan","Trish");
        // List<String> phoneNumber = List.of("95546546664", "1254665556");
        // ContactBean contactBean 
        //         = new ContactBean(name, phoneNumber, null, null, null, null);
        // //contactBeanList.add(contactBean);
        // sio.writeContacts(contactBean, "friends");

        // name = new Name(Pronoun.she_her,"Aishwarya Rai","Aishu");
        // phoneNumber = List.of("45896546664", "3154665556");
        // contactBean 
        //          = new ContactBean(name, phoneNumber, null, null, null, null);
        // // //contactBeanList.add(contactBean);
        //  sio.writeContacts(contactBean, "friends");

       System.out.println(sio.readContact("friends"));
    }
}
