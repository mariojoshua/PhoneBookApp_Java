package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.sql.Date;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

public class MySqlContactWriter {

    PreparedStatement ps1 = null;
    private PreparedStatement ps2 = null;
    PreparedStatement ps3 = null;
    ResultSet resultSet = null;
    MySqlService mySqlService = null;

    public MySqlContactWriter() {
       this.mySqlService = new MySqlService();
    }

    public boolean write(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            // insert into contacts table
            int contacts_ID = insertIntoContactsTable(contactBean);
            int phoneNumber_ID = insertIntoPhoneNumberTable(contactBean, contacts_ID);
            int email_ID = insertIntoEmailTable(contactBean,contacts_ID);
            //int tags_ID = insertIntoTagsTable(contactBean,contacts_ID);
            connection.commit();
            Logger.getInstance().log("writeContacts method finished");
            return true;
        } catch (SQLException se) {
            Logger.getInstance().log("write\n" +  se.getMessage());
            return false;
        }
    }

    int insertIntoContactsTable(ContactBean contactBean) {
        try(Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            int phonebook_ID = new MySqlService().getPhonebook_ID(contactBean);
            ps1 = connection.prepareStatement(
                """
                INSERT INTO contacts
                (phonebook_ID, gender, fullname, petname, dateOfBirth, address)
                VALUES(?,?,?,?,?,?)""",
                PreparedStatement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, phonebook_ID);
            ps1.setString(2, contactBean.name().getGender().name());
            ps1.setString(3,contactBean.name().getFullName());
            ps1.setString(4, contactBean.name().getPetName());
            Date date = new java.sql.Date(Date.valueOf(contactBean.dates().get("dateOfBirth")).getTime());
            ps1.setDate(5, date);
            ps1.setString(6, contactBean.address());
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("insertIntoContactsTable rowsAffected= " + rowsAffected);
            resultSet = ps1.getGeneratedKeys();
            int contacts_ID = -1;
            if (resultSet.next()) {
                contacts_ID = resultSet.getInt(1);
            }
            Logger.getInstance().log("insertIntoContactsTable generatedKey contacts_ID= " + contacts_ID);
            return rowsAffected;
        }   catch (SQLException e) {
            Logger.getInstance().log("insertIntoContactsTable\n" +  e.getMessage());
            return -1;
        }    
    }

    int insertTags(ContactBean contactBean, int contacts_ID) {
        int tag_ID = 0;
        List<Integer> tagIDList = new LinkedList<>();
        // checkIfTagExists and add tag id to List of tags
        // If not create tags in tags table and add tag id to List of tags
        for (String tag: contactBean.tags()) {
            tag_ID = mySqlService.checkIfTagExistsInTagsTable(tag);
            if (tag_ID != 0) { //if tag exists
                tagIDList.add(tag_ID);
            } else {
                tag_ID = insertIntoTagsTable(tag, contacts_ID);
                tagIDList.add(tag_ID);
            }
        }
        Logger.getInstance().log("insertIntoTagsTable tagIDList= " + tagIDList.toString());
        // with the ids generated insert into contacts_tags table
        int rowsAffected = insertIntoContactsTagsLinkTable(tagIDList, contacts_ID);
        return rowsAffected;
    }    

    int insertIntoTagsTable(String tag, int contacts_ID) {
        try(Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            int rowsAffected = 0;
            int tag_ID = -1;
            List<Integer> tagIDList = new LinkedList<>();
            ps2 = connection.prepareStatement(
                """
                INSERT INTO contactApp.tags
                (tag)
                VALUES(?)""", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, tag); 
            rowsAffected += ps2.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            resultSet = ps2.getGeneratedKeys();
            while (resultSet.next()) {
                tag_ID = resultSet.getInt(1);
            }
            Logger.getInstance().log("insertIntoTagsTable tag_Id = " + tag_ID);
            Logger.getInstance().log("insertIntoTagsTable executeUpdate rowsAffected = " + rowsAffected);
            return tag_ID;
        } catch (SQLException e) {
            Logger.getInstance().log("insertIntoTagsTable\n" + e.getMessage());
            return -1;
        }  
    }

    int insertIntoContactsTagsLinkTable(List<Integer> tagIDList,  int contacts_ID)  {
        try (Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            int rowsAffected = 0;
            for (int tag_ID: tagIDList) {
                System.out.println(tag_ID);
                ps3 = connection.prepareStatement
                    ("""
                    INSERT INTO contactApp.contacts_tags
                    (contacts_ID, tag_ID)
                    VALUES(?,?)""");
                ps3.setInt(1, contacts_ID);
                ps3.setInt(2, tag_ID);
                rowsAffected += ps3.executeUpdate();
                MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            }
            Logger.getInstance().log("insertIntoContactsTagsLinkTable rowsAffected = " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            //Overload log method to take array of Stack Track elements as 2nd parameter
            Logger.getInstance().log("insertIntoContactsTagsLinkTable\n" +  e.getMessage());
            return -1;
        }  
    }

    int insertIntoEmailTable(ContactBean contactBean, int contacts_ID) {
        try(Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            int rowsAffected = 0;
            for (String emailid: contactBean.email()) {
                ps2 = connection.prepareStatement(
                    """
                    INSERT INTO contactApp.email
                    (contacts_ID, emailid)
                    VALUES( ?, ?)""");
                ps2.setInt(1, contacts_ID);    
                ps2.setString(2, emailid); 
                rowsAffected +=  ps2.executeUpdate(); 
            }
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("insertIntoEmailTable rowsAffected = " + rowsAffected);     
            return rowsAffected;
        } catch (SQLException e) {
            Logger.getInstance().log("insertIntoEmailTable\n" +  e.getMessage());
            return -1;
        }  
    }

    int insertIntoPhoneNumberTable(ContactBean contactBean, int contacts_ID) {
        try(Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false); 
            int rowsAffected = 0;
            for (String phoneNumber: contactBean.phoneNumbers()) {
                ps2 = connection.prepareStatement(
                    """
                    INSERT INTO contactApp.phonenumber
                    (contacts_ID, phoneNumber)
                    VALUES( ?, ?)""");
                ps2.setInt(1, contacts_ID);
                ps2.setString(2, phoneNumber);
                rowsAffected += ps2.executeUpdate(); 
            }
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("insertIntoPhoneNumberTable rowsAffected = " + rowsAffected); 
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getInstance().log("insertIntoPhoneNumberTable\n" +  e.getMessage());
            return -1;
        }  
    }
}
