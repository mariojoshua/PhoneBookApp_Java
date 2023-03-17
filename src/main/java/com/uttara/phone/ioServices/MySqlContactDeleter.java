package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

public class MySqlContactDeleter {
    
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;

    public Boolean delete(String fullName) {
        return false;
    }

    Boolean deleteFromContactsTable(String fullName) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contactApp.contacts
            WHERE fullname = ?;""");
            ps1.setString(1, fullName);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromContactsTable executeUpdate rowsAffected = " + rowsAffected);
            return true;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }

    Boolean deleteFromEmailTable(int contacts_id) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contactApp.email
            WHERE contacts_id = ?;""");
            ps1.setInt(1, contacts_id);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromEmailTable executeUpdate rowsAffected = " + rowsAffected);
            return true;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }

    Boolean deleteFromPhoneNumberTable(int contacts_id) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contacts
            WHERE contacts_id = ?;""");
            ps1.setInt(1, contacts_id);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromContactsTable executeUpdate rowsAffected = " + rowsAffected);
            return true;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }

    List<Integer> getTags_ID(int contacts_id) {
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT ID 
                FROM phonebook_master 
                WHERE name = ?""" );
            ps1.setString(1, contactBean.phoneBookName());
            resultSet = ps1.executeQuery();
            int phonebook_ID = -1;
            List<Integer> returnList = new LinkedList<Integer>();
            while (resultSet.next()) {
                returnList.add(ps1.getResultSet().getInt("ID"));
            }
            Logger.getInstance().log("getPhonebook_ID method phonebook_ID= " + phonebook_ID);
            return phonebook_ID != 0 ? : Collections.emptyList();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList() ;
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
    
    Boolean deleteFromTagsTable(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contacts
            WHERE contacts_id = ?;""");
            ps1.setString(1, fullName);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromContactsTable executeUpdate rowsAffected = " + rowsAffected);
            return true;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }

    Boolean deleteFromContactsTagsLinkTable(int contacts_id) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contacts
            WHERE contacts_id = ?;""");
            ps1.setInt(1, contacts_id);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromContactsTable executeUpdate rowsAffected = " + rowsAffected);
            return true;
        } catch (SQLException e) { 
            e.printStackTrace();
            return false;
        }
    }

    
}
