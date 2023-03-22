package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

public class MySqlContactDeleter {
    
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    public MySqlService mySqlService = null;

    public MySqlContactDeleter() {
        this.mySqlService = new MySqlService();
    }

    public Boolean delete(String fullName) {
        int contacts_ID = mySqlService.getContacts_ID(fullName);
        deleteTags(contacts_ID);
        deleteFromEmailTable(contacts_ID);
        deleteFromPhoneNumberTable(contacts_ID);
        int result = deleteFromContactsTable(fullName);
        // delete tag only if count of tag is 1 in tags/tags link table
        return result!= -1 ? true : false;
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
            Logger.getInstance().log("deleteFromContactsTable" + e.getMessage());
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
            Logger.getInstance().log("deleteFromEmailTable"+ e.getMessage());
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
            Logger.getInstance().log("deleteFromPhoneNumberTable" + e.getMessage());
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
            Logger.getInstance().log("deleteFromTagsTable" + e.getMessage());
            return -1;
        }
    }

    int deleteTags(int contacts_ID) {
        Logger.getInstance().log("deleteTags method contacts_ID = " + contacts_ID);
        int rowsAffected = 0;
        //get tag_id's from contact id
        List<Integer> tagsList =  mySqlService.getTags_IDFromContacts_TagsTable(contacts_ID);
        // in contacts_tags table, if tag exists only for 1 contact     
        //delete tag
        Map<Integer,Integer>linkedContactsMap = new LinkedHashMap<>();
        Logger.getInstance().log(linkedContactsMap.toString());
        //Logger.getInstance().log(linkedContactsMap.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.joining("|")));
        tagsList.forEach(tag_ID -> linkedContactsMap.put(tag_ID, numberOfContactsLinkedWithTags(tag_ID)));
        rowsAffected += deleteFromContactsTagsLinkTable(contacts_ID);
        for (int tag_ID: tagsList) {
            if (linkedContactsMap.get(tag_ID) == 1) {
                rowsAffected += deleteFromTagsTable(tag_ID);
            }
        }
        //else keep tag and delete entries from contacts_tags table
        Logger.getInstance().log("deleteTags method rowsAffected = " + rowsAffected);
        return rowsAffected;
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
            Logger.getInstance().log("numberOfContactsLinkedWithTags" + e.getMessage());
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
            Logger.getInstance().log("deleteFromContactsTagsLinkTable" + e.getMessage());
            return -1;
        }
    }

    
}
