package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

public class MySqlContactWriter {

    private PreparedStatement ps1 = null, ps2 = null;
    private ResultSet resultSet = null;
    private Connection connection = null;

    public boolean write(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            // insert into contacts table
            int contacts_ID = insertIntoContactsTable(contactBean);
            int phoneNumber_ID = insertIntoPhoneNumberTable(contactBean, contacts_ID);
            int email_ID = insertIntoEmailTable(contactBean,contacts_ID);
            int tags_ID = insertIntoTagsTable(contactBean,contacts_ID);
            connection.commit();
            Logger.getInstance().log("writeContacts method finished");
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    private int insertIntoContactsTable(ContactBean contactBean) throws SQLException {
        ps1 = connection.prepareStatement(
                """
                INSERT INTO contactApp.contacts\
                (phonebook_ID, gender, fullname, petname, dateOfBirth, address)\
                VALUES((SELECT ID FROM phonebook.master WHERE ID ="""
                + contactBean.phoneBookName() + ";),?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ps1.setString(2, contactBean.name().getGender().name());
            ps1.setString(3,contactBean.name().getFullName());
            ps1.setString(4, contactBean.name().getPetName());
            Date date = new java.sql.Date(Date.valueOf(contactBean.dates().get("dateOfBirth")).getTime());
            ps1.setDate(5, date);
            ps1.setString(6, contactBean.address());
            Logger.getInstance().log("insertIntoContactsTable executeUpdate return Value= " + ps1.executeUpdate());
            resultSet = ps1.getGeneratedKeys();
            int contacts_ID = resultSet.getInt("ID");
            return contacts_ID;
    }

    private int insertIntoTagsTable(ContactBean contactBean, int contacts_ID) throws SQLException {
        for (String tag: contactBean.tags()) {
            ps2 = connection.prepareStatement(
                """
                INSERT INTO contactApp.tags
                (tag)
                VALUES(?,""" + Statement.RETURN_GENERATED_KEYS + ")");
            ps2.setString(1, tag); 
            Logger.getInstance().log("insertIntoTagsTable executeUpdate return value = " + ps2.executeUpdate());
            resultSet = ps2.getGeneratedKeys();
            int tag_ID = resultSet.getInt("ID");
            // insert into Contacts_tags link table
            insertIntoContactsTagsLinkTable(tag_ID, contacts_ID);
        }    
        return 0;
    }

    private int insertIntoContactsTagsLinkTable(int tag_ID,  int contacts_ID) throws SQLException {
        PreparedStatement ps3 = connection.prepareStatement
        ("""
        INSERT INTO contacts_tags
        (contacts_ID, tag_ID)
        VALUES(?,?)""");
        ps3.setInt(1, contacts_ID);
        ps3.setInt(2, tag_ID);
        Logger.getInstance().log("insertIntoContactsTagsLinkTable executeUpdate value = " + ps3.executeUpdate());
        return 0;
    }

    private int insertIntoEmailTable(ContactBean contactBean, int contacts_ID) throws SQLException {
        for (String emailid: contactBean.email()) {
            ps2 = connection.prepareStatement(
                """
                INSERT INTO contactApp.email
                (contacts_ID,emailid)
                VALUES(""" + contacts_ID +",?)");
            ps2.setString(2, emailid); 
            Logger.getInstance().log("insertIntoEmailTable executeUpdate value" + ps2.executeUpdate());  
        }
        return 0;
    }

    private int insertIntoPhoneNumberTable(ContactBean contactBean, int contacts_ID) throws SQLException {
        for (String phoneNumber: contactBean.phoneNumbers()) {
            ps2 = connection.prepareStatement(
                """
                INSERT INTO contactApp.phonenumber
                (contacts_ID,phoneNumber)
                VALUES(""" + contacts_ID +",?)");
            ps2.setString(0, phoneNumber);
            Logger.getInstance().log("insertIntoPhoneNumberTable executeUpdate value" + ps2.executeUpdate());  
        }
        return 0;
    }


}
