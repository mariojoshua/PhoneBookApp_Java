package main.java.com.uttara.phone;

import java.util.List;

public abstract class IOService {

    public abstract List<ContactBean> readContact(String phoneBookName);
    public abstract Boolean writeContacts(ContactBean contactBean, String phoneBookName);
    public abstract Boolean updateContacts(ContactBean contactBean);
    public abstract Boolean createContactBook(String phoneBookName);
    public abstract Boolean deleteContact(String fullName, String phoneBookName);
    public abstract Boolean deleteContactBook(String phoneBookName);
    public abstract Boolean contactBookExists(String phoneBookName);
    public abstract Boolean contactExists(String fullName ,String phoneBookName);
}