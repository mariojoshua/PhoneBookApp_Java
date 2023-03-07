package main.java.com.uttara.phone;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;



public class SerializedTextIOService extends IOService {

    @Override
    public  List<ContactBean> readContact(String phoneBookName) {
        List<ContactBean> contactBean = new LinkedList<ContactBean>();
        try (ObjectInputStream objectInputStream 
                = new ObjectInputStream(new FileInputStream(phoneBookName + ".ser"))) {
            return Collections.unmodifiableList
                    ((List<ContactBean>)objectInputStream.readObject());         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactBean;
    }

    @Override
    public Boolean writeContacts(ContactBean contactBean,String phoneBookName) {
        try(ObjectOutputStream objectOutputStream 
            = new ObjectOutputStream(new FileOutputStream(phoneBookName+".ser"))) {
            objectOutputStream.writeObject(readContact(phoneBookName).add(contactBean));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateContacts(ContactBean contactBean, String phoneBookName) {
        try(ObjectOutputStream objectOutputStream 
            = new ObjectOutputStream(new FileOutputStream(phoneBookName+".ser"))) {
            objectOutputStream.writeObject(readContact(phoneBookName).add(contactBean));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean createContactBook(String phoneBookName) {
        try (FileOutputStream fos = new FileOutputStream(phoneBookName +".ser")) {
            return new File(phoneBookName + ".ser").exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }        
    }

    @Override
    public Boolean deleteContactBook(String phoneBookName) {
        return new File(phoneBookName).delete();
    }

    @Override
    public Boolean contactBookExists(String phoneBookName) {
        return new File(phoneBookName).exists();
    }



    @Override
    public Boolean contactExists(String fullName, String phoneBookName) {
        return readContact(phoneBookName).stream()
        .filter(contactBean -> contactBean.name().getFullName().equals(fullName))
        .findFirst()
        .isPresent();
    }
        
    
    @Override
    public Boolean deleteContact(String fullName, String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean updateContacts(ContactBean contactBean) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateContacts'");
    }

}
