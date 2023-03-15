package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            //rewrite delete    
            """
            DELETE FROM contacts
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

    Boolean deleteFromContactsTagsLinkTable(ContactBean contactBean) {
        return false;
    }

    Boolean deleteFromEmailTable(ContactBean contactBean) {
        return false;
    }

    Boolean deleteFromPhoneNumberTable(ContactBean contactBean) {
        return false;
    }

    Boolean deleteFromTagsTable(ContactBean contactBean) {
        return false;
    }

    
}
