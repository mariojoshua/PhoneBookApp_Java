package com.uttara.phone.ioServices.mySqlService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.ioServices.IOService;

/**
 * This class will hold the view and the controller in flavor 1 of MVC(Desktop
 * App) design pattern
 * 
 * @author mariojoshuaaugustine
 * @version 1.0
 * @since 2021-12-01
 * 
 */

public class MySqlService extends IOService {

    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    Connection connection = null;

    /**
	 * This method creates a phoneBookName record in the MySQL phonebook_master
     * table 
	 * 
	 * @param 
	 * @return 
	 * true if it was successfully created or false if theres an exception
	 */
    @Override
    public Boolean createContactBook(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()) {
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

    /**
	 * This method checks if a phone books name exists in the in 
     * phonebook_master table
	 * 
	 * @param 
	 * @return 
	 * true if the contactBook exists , false if
     * it doesnt exist or if there's an exception
	 */
    @Override
    public Boolean contactBookExists(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement(
                """
                SELECT name
                FROM contactApp.phonebook_master
                WHERE name LIKE ?;""");
            ps1.setString(1, "%" + phoneBookName + "%");
            resultSet = ps1.executeQuery();
            String nameFromDatabase = "";
            while (resultSet.next()) {
                nameFromDatabase = resultSet.getString("name");
                Logger.getInstance().log("phoneBook nameFromDatabase = " + nameFromDatabase);
            }
            boolean returnValue = phoneBookName.toUpperCase().equals(nameFromDatabase) ? true : false;
            Logger.getInstance().log("contactBookExists method return value = " + returnValue);
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
	 * This method is used to delete the phoneBookName from the 
     * phonebook_master database 
	 * 
	 * @param 
	 * @return 
	 * true if the deletion was succesful, false if it failed
	 */
    @Override
    public Boolean deleteContactBook(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()) {
            // Give warning that all contacts will also be deleted, delete all contacts
            // first and then delte the contact book
            // deleteContact("IS NOT NULL", phoneBookName);
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


    /**
	 * This method is used to input the contact details into the mysql database
	 * 
	 * @param 
	 * @return 
	 * true if succesfully written, false if it failed
	 */
    @Override
    public Boolean writeContacts(ContactBean contactBean) {
        MySqlContactWriter contactWriter = new MySqlContactWriter();
        return contactWriter.write(contactBean);
    }

    /**
	 * This method check if a contact, specifically the full name exists in
     * the contacts table
	 * 
	 * @param 
	 * @return 
	 * true if the contact exists, false if it doesnt.
	 */
    @Override
    public Boolean contactExists(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()) {
            int phonebook_ID = getPhonebook_ID(contactBean);
            ps1 = connection.prepareStatement(
                """
                SELECT fullname
                FROM contacts
                WHERE phonebook_ID = ?
                    AND fullname LIKE ? ;""");
            ps1.setInt(1, phonebook_ID);
            ps1.setString(2, contactBean.name().getFullName());
            boolean returnValue = ps1.executeQuery().next();
            Logger.getInstance().log("contactExists method executeQuery() return value = " + returnValue);
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
	 * This method get the joined table from the MySql database, 
     * processes the data using streams and puts it into a 
     * list of ContactBean elements and returns the list 
	 * 
	 * @param 
	 * @return 
	 * ArrayList<ContactBean>
	 */
    @Override
    public Boolean deleteContact(String phoneBookName, String fullName) {
        MySqlContactDeleter mySqlContactDeletor = new MySqlContactDeleter();
        return mySqlContactDeletor.delete(fullName);
    }


    /**
	 * This method gets all the data from the mysql database which are
     * linked to a phoneBookNAme
	 * 
	 * @param 
	 * @return 
	 * A list of contactBean Objects
	 */
    @Override
    public List<ContactBean> readContact(String phoneBookName) {
        //see if phone number exists
        MySqlContactReader mySqlContactReader = new MySqlContactReader();
        return mySqlContactReader.read(phoneBookName);
    }

    /**
	 * This method get the joined table from the MySql database, 
     * processes the data using streams and puts it into a 
     * list of ContactBean elements and returns the list 
	 * @hidden
	 * @param 
	 * @return 
	 * ArrayList<ContactBean>
	 */
    @Override
    public Boolean updateContacts(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()) {
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement("""
                UPDATE users
                SET name = 'barfoo', email = 'bar@foo.com'
                WHERE email = 'foo@bar.com';""");
            int rowsAffected = ps1.executeUpdate();
            System.out.println(rowsAffected + " row(s) has been deleted!");
            connection.commit();
            return true;
        } catch (SQLException e) {
            // rollbackSQLCommit(connection);
            e.printStackTrace();
            return false;
        }
    }

    /**
	 * This method gets the id associated with the phonebook name in the 
     * phonebook_master mysql database table when it got created in the database
     * to be used as a foreign key
	 * 
	 * @param 
	 * @return 
	 * int id associated with a phoneBookName
	 */
    int getPhonebook_ID(ContactBean contactBean) {
        try (Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT ID
                FROM phonebook_master
                WHERE name = ?""");
            ps1.setString(1, contactBean.phoneBookName());
            resultSet = ps1.executeQuery();
            int phonebook_ID = -1;
            while (resultSet.next()) {
                phonebook_ID = ps1.getResultSet().getInt("ID");
            }
            Logger.getInstance().log("getPhonebook_ID method phonebook_ID= " + phonebook_ID);
            return phonebook_ID != 0 ? phonebook_ID : -1;
        } catch (SQLException e) {
            Logger.getInstance().log("getPhonebook_ID\n" + e.getStackTrace().toString());
            return -1;
        }
    }

    /**
	 * This method gets the id associated with the contact name in the 
     * contacts mysql database table when it got created in the database
     * to be used as a foreign key
	 * 
	 * @param 
	 * @return 
	 * int id associated with a contact
	 */
    int getContacts_ID(String fullName) {
        try (Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                    SELECT ID
                    FROM contactApp.contacts
                    WHERE fullname = ?""");
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
            return -1;
        }
    }

    /**
	 * This method gets the tag id from the Contacts_tags link table in
     * order to be used to find the tag associated with a contact
     * for quick searching
	 * 
	 * @param 
	 * @return 
	 * List id's associated with a contacts_tag link table record
	 */
    List<Integer> getTags_IDFromContacts_TagsTable(int contacts_id) {
        try (Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                    SELECT tag_ID
                    FROM contactApp.contacts_tags
                    WHERE contacts_ID = ?""");
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
            return Collections.emptyList();
        }
    }

    /**
	 * This method checks if a tag exists in the tags table and returns 
     * the id associated with a tag to be used a foreign key in the
     * contacts_tags table
	 * 
	 * @param 
	 * @return 
	 * tag id if it exists, 0 if the tag doesnt exist
	 */
    int checkIfTagExistsInTagsTable(String tag) {
        int tag_ID = 0;
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT ID 
                FROM contactApp.tags 
                WHERE tag = ?""" );
            ps1.setString(1, tag);
            resultSet = ps1.executeQuery();
            while (resultSet.next()) {
                tag_ID = ps1.getResultSet().getInt("ID");
            }
            Logger.getInstance().log("checkIfTagExistsInTagsTable exists = " + tag_ID);
            return tag_ID;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }    
    }

    //Only for testing 
    //used to clear the remnant data from the database when a test fail
    void deleteAll() {
        try(Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement(
                """
                DELETE FROM contactApp.contacts_tags;
                DELETE FROM contactApp.tags;
                DELETE FROM contactApp.phonenumber;
                DELETE FROM contactApp.email;
                DELETE FROM contactApp.contacts;
                DELETE FROM contactApp.phonebook_master;""");
            resultSet = ps1.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }    
    }



}
