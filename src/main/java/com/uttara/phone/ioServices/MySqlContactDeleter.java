package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

public class MySqlContactDeleter {
    
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    public MySqlService mySqlService = null;

    public Boolean delete(String fullName) {

        // delete tag only if count of tag is 1 in tags/tags link table
        return false;
    }

    int deleteFromContactsTable(String fullName) {
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
            return rowsAffected;
        } catch (SQLException e) { 
            e.printStackTrace();
            return 0;
        }
    }

    int deleteFromEmailTable(int contacts_id) {
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
            return rowsAffected;
        } catch (SQLException e) { 
            e.printStackTrace();
            return -1;
        }
    }

    int deleteFromPhoneNumberTable(int contacts_id) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contactApp.phonenumber
            WHERE contacts_id = ?;""");
            ps1.setInt(1, contacts_id);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromPhoneNumberTable method executeUpdate rowsAffected = " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) { 
            e.printStackTrace();
            return 0;
        }
    }

    int deleteFromTagsTable(int tag_ID) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            int rowsAffected = 0;
            ps1 = connection.prepareStatement(  
                """
                DELETE FROM contactApp.tags
                WHERE ID = ?;""");
            ps1.setInt(1, tag_ID);
            rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromTagsTable executeUpdate rowsAffected = " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) { 
            e.printStackTrace();
            return -1;
        }
    }

    int deleteTags(int contacts_ID) {
        //get tag_id's from contact id
        List<Integer> tagsList =  mySqlService.getTags_ID(contacts_ID);
        // in contacts_tags table, if tag exists only for 1 contact
        for (int tag_ID: tagsList) {
            if (numberOfContactsLinkedWithTags(tag_ID) == 1) {
                deleteFromTagsTable(tagsList);
            }
        }
        //delete tag
        //else keep tag and delete entries from contacts_tags table
        return -1;
    }

    int numberOfContactsLinkedWithTags(int tag_ID) {
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT COUNT(DISTINCT(contacts_ID)) 
                FROM contactApp.contacts_tags 
                WHERE tag_ID = ?""" );
            ps1.setInt(1, tag_ID);
            resultSet = ps1.executeQuery();
            int uniqueContactCount = 0;
            while (resultSet.next()) {
                uniqueContactCount = resultSet.getInt(1);
            }
            Logger.getInstance().log("numberOfContactsLinkedWithTags uniqueContactCount= " + uniqueContactCount);
            return uniqueContactCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1 ;
        }
    }



    int deleteFromContactsTagsLinkTable(int contacts_id) {
        try (Connection connection = MySqlHelper.getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(  
            """
            DELETE FROM contactApp.contacts_tags
            WHERE contacts_id = ?;""");
            ps1.setInt(1, contacts_id);
            int rowsAffected = ps1.executeUpdate();
            MySqlHelper.isUpdateExecutedOrNot(rowsAffected, connection);
            Logger.getInstance().log("deleteFromContactsTagsLinkTable rowsAffected = " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) { 
            e.printStackTrace();
            return -1;
        }
    }

    
}
