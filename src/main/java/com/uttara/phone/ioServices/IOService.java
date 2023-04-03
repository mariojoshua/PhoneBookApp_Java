package com.uttara.phone.ioServices;

import java.util.List;

import com.uttara.phone.ContactBean;

/**
 * This is an abstract class giving the headers for the methods to be implemented 
 * by any form of persistence technology like plaintex,sql,nosql 
 * which extends this class.
 * 
 * @author mariojoshuaaugustine
 * @version 1.0
 * @since 2021-12-01
 * 
 */

public abstract class IOService {

    public abstract List<ContactBean> readContact(String phoneBookName);
    public abstract Boolean writeContacts(ContactBean contactBean);
    public abstract Boolean updateContacts(ContactBean contactBean);
    public abstract Boolean createContactBook(String phoneBookName);
    public abstract Boolean deleteContact(String phoneBookName, String fullName);
    public abstract Boolean deleteContactBook(String phoneBookName);
    public abstract Boolean contactBookExists(String phoneBookName);
    public abstract Boolean contactExists(ContactBean contactBean);
}