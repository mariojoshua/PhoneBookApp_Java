package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLTransientConnectionException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

public class MySqlService extends IOService {

    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    Connection connection = null;
    MySqlContactWriter mWriter = new MySqlContactWriter();

    
    @Override
    public Boolean createContactBook(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(
            """
            INSERT INTO contactApp.phonebook_master 
            (name) 
            VALUES(?)""");
            ps1.setString(1, phoneBookName.toUpperCase());
            int returnValue = ps1.executeUpdate();
            Logger.getInstance().log("createContactBook method executeUpdate() return value = " + returnValue);
            return MySqlHelper.isUpdateExecutedOrNot(returnValue, connection);
        } catch (SQLException e) {    
            e.printStackTrace();
            return false;
        }
              
    }

    @Override
    public Boolean contactBookExists(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()){
            ps1 = connection.prepareStatement(
                """
                SELECT name
                FROM contactApp.phonebook_master 
                WHERE name LIKE ?;""");
            ps1.setString(1,"%" + phoneBookName + "%");
            resultSet = ps1.executeQuery(); 
            String nameFromDatabase = "";
            while (resultSet.next()) {              
                nameFromDatabase = resultSet.getString("name");       
                Logger.getInstance().log("phoneBook nameFromDatabase = " + nameFromDatabase);
            }
            boolean returnValue = phoneBookName.toUpperCase().equals(nameFromDatabase) ? true : false;
            Logger.getInstance().log("contactBookExists method return value = " + returnValue );
            return returnValue;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteContactBook(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()){
            // Give warning that all contacts will also be deleted, delete all contacts first and then delte the contact book
            //deleteContact("IS NOT NULL", phoneBookName);
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(
            """
            DELETE FROM contactApp.phonebook_master 
            WHERE name = ?;""");
            ps1.setString(1, phoneBookName);
            int returnValue = ps1.executeUpdate();
            Logger.getInstance().log("deleteContactBook method executeUpdate() return value = " + returnValue);
            return MySqlHelper.isUpdateExecutedOrNot(returnValue, connection);
        } catch (SQLException e) {   
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public List<ContactBean> readContact(String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Boolean writeContacts(ContactBean contactBean) {
        MySqlContactWriter contactWriter = new MySqlContactWriter();
        return contactWriter.write(contactBean);
    }

   

    @Override
    public Boolean updateContacts(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(
        """
        UPDATE users
        SET name = 'barfoo', email = 'bar@foo.com'
        WHERE email = 'foo@bar.com';
        """);
        int rowsAffected = ps1.executeUpdate();
        System.out.println(rowsAffected + " row(s) has been deleted!");
        connection.commit();
        return true;
    } catch (SQLException e) {
        //rollbackSQLCommit(connection);    
        e.printStackTrace();
        return false;
    }
    }

    @Override
    public Boolean deleteContact(String phoneBookName, String fullName) {
        MySqlContactDeleter mySqlContactDeletor = new MySqlContactDeleter();
        return mySqlContactDeletor.delete(fullName);
    }

    @Override
    public Boolean contactExists(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()){
            int phonebook_ID = getPhonebook_ID(contactBean);
            ps1 = connection.prepareStatement(
            """
            SELECT fullname
            FROM contacts 
            WHERE phonebook_ID = ?
              AND fullname LIKE ? ;""");
            ps1.setInt(1, phonebook_ID);
            ps1.setString(2, contactBean.name().getFullName());
            return ps1.executeQuery().next();
        } catch (SQLException e) {
            //rollbackSQLCommit(connection);    
            e.printStackTrace();
            return false;
        }
    }

    int getPhonebook_ID(ContactBean contactBean) {
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT ID 
                FROM phonebook_master 
                WHERE name = ?""" );
            ps1.setString(1, contactBean.phoneBookName());
            resultSet = ps1.executeQuery();
            int phonebook_ID = -1;
            while (resultSet.next()) {
                phonebook_ID = ps1.getResultSet().getInt("ID");
            }
            Logger.getInstance().log("getPhonebook_ID method phonebook_ID= " + phonebook_ID);
            return phonebook_ID != 0 ? phonebook_ID : -1 ;
        } catch (SQLException e) {
            Logger.getInstance().log("getPhonebook_ID\n" +  e.getStackTrace().toString());
            return -1;
        }
    }

    int getContacts_ID(String fullName) {
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT ID 
                FROM contactApp.contacts 
                WHERE fullname = ?""" );
            ps1.setString(1, fullName);
            resultSet = ps1.executeQuery();
            int contacts_ID = -1;
            while (resultSet.next()) {
                contacts_ID = ps1.getResultSet().getInt("ID");
            }
            Logger.getInstance().log("getContacts_ID method phonebook_ID= " + contacts_ID);
            return contacts_ID;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1 ;
        }
    }

    List<Integer> getTags_ID(int contacts_id) {
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT tag_ID 
                FROM contactApp.contacts_tags 
                WHERE contacts_ID = ?""" );
            ps1.setInt(1, contacts_id);
            resultSet = ps1.executeQuery();
            List<Integer> returnList = new LinkedList<Integer>();
            while (resultSet.next()) {
                returnList.add(ps1.getResultSet().getInt("tag_ID"));
            }
            Logger.getInstance().log("getTags_ID method tags_ID count = " + returnList.size());
            return returnList.size() != 0 ? returnList : Collections.emptyList();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList() ;
        }
    }


    
}
