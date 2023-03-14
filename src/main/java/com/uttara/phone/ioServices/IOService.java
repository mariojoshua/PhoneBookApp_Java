package com.uttara.phone.ioServices;

import java.util.List;

import com.uttara.phone.ContactBean;

public abstract class IOService {

    public abstract List<ContactBean> readContact(String phoneBookName);
    public abstract Boolean writeContacts(ContactBean contactBean);
    public abstract Boolean updateContacts(ContactBean contactBean);
    public abstract Boolean createContactBook(String phoneBookName);
    public abstract Boolean deleteContact(String fullName, String phoneBookName);
    public abstract Boolean deleteContactBook(String phoneBookName);
    public abstract Boolean contactBookExists(String phoneBookName);
    public abstract Boolean contactExists(ContactBean contactBean);
}