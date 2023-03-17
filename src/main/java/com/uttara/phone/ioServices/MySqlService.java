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
                Logger.getInstance().log("nameFromDatabase = " + nameFromDatabase);
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
            int phonebook_ID = mWriter.getPhonebook_ID(contactBean);
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


    
}
