package main.java.com.uttara.phone;

import java.util.List;

public interface IOService {
    public static final String phoneBookName;
    public List<ContactBean> readContact(String phoneBookName);
    public Boolean writeContacts(ContactBean contactBean, String phoneBookName);
    public Boolean updateContacts(ContactBean contactBean);
    public Boolean createContactBook(String phoneBookName);
    public Boolean deleteContact(String fullName, String phoneBookName);
    public Boolean deleteContactBook(String phoneBookName);
    public Boolean contactBookExists(String phoneBookName);
    public Boolean contactExists(String fullName ,String phoneBookName);
}