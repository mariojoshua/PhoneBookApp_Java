package main.java.com.uttara.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySqlService extends IOService {

    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    Connection connection = null;

    private Connection getConnection() {
		String URL = "jdbc:mysql://localhost:3306/";
		String UID = "root";
		String PASS = "";
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		try {
            connection = DriverManager.getConnection(URL,UID,PASS);
			Class.forName(DRIVER);
			System.out.println("Established Connection Succesfully "+ connection);
			connection.prepareStatement("USE contactAPP");
            return connection;
		} catch (ClassNotFoundException |SQLException se) {
			rollbackSQLCommit(connection);
            se.printStackTrace();
		}
		return null;
    }

    private static void rollbackSQLCommit(Connection connection) {
		if(connection !=null)
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

    @Override
    public Boolean createContactBook(String phoneBookName) {
        try (Connection connection = getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(
            """
            INSERT INTO contactApp.phonebook_master 
            (name) 
            VALUES(?)""");
            ps1.setString(1, phoneBookName.toUpperCase());
            if (ps1.executeUpdate() != 0) {
                connection.commit();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            rollbackSQLCommit(connection);    
            e.printStackTrace();
            return false;
        }
              
    }

    @Override
    public Boolean contactBookExists(String phoneBookName) {
        try (Connection connection = getConnection()){
            ps1 = connection.prepareStatement(
            """
            SELECT name
            FROM contactApp.phonebook_master 
            WHERE name = '?');""");
            ps1.setString(1, phoneBookName.toUpperCase());
            Boolean result = ps1.execute(); 
            return result;
        } catch (SQLException e) {
            rollbackSQLCommit(connection);    
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
    public Boolean writeContacts(ContactBean contactBean, String phoneBookName) {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); 
            int contacts_ID = insertIntoContactsTable(contactBean, phoneBookName);
            int phoneNumber_ID = insertIntoPhoneNumberTable(contactBean, contacts_ID);
            int email_ID = insertIntoEmailTable(contactBean,contacts_ID);
            int tags_ID = insertIntoTagsTable(contactBean,contacts_ID);
            connection.commit();
            return true;
        } catch (SQLException se) {
            rollbackSQLCommit(connection);
            se.printStackTrace();
            return false;
        }
    }

    private int insertIntoTagsTable(ContactBean contactBean, int contacts_ID) throws SQLException {
        for (String tag: contactBean.tags()) {
            ps2 = connection.prepareStatement(
                """
                INSERT INTO contactApp.tags
                (tag)
                VALUES(?,""" + Statement.RETURN_GENERATED_KEYS + ")");
            ps2.setString(1, tag); 
            ps2.executeQuery(); 

            resultSet = ps2.getGeneratedKeys();
            int tag_ID = resultSet.getInt("ID");
        }    
        return 0;
    }

    public int insertIntoContactsTagsLinkTable(int tag_ID,  int contacts_ID) throws SQLException {
        PreparedStatement ps3 = connection.prepareStatement
        ("""
        INSERT INTO contacts_tags
        (contacts_ID, tag_ID)
        VALUES(?,?)""");
        ps3.setInt(1, contacts_ID);
        ps3.setInt(2, tag_ID);
        ps3.executeQuery();
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
            ps2.executeQuery();  
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
            ps2.executeQuery();   
        }
        return 0;
    }


    private int insertIntoContactsTable(ContactBean contactBean, String phoneBookName) throws SQLException {
        ps1 = connection.prepareStatement(
                """
                INSERT INTO contactApp.contacts\
                (phonebook_ID, gender, fullname, petname, dateOfBirth, address)\
                VALUES((SELECT ID FROM phonebook.master WHERE ID ="""
                + phoneBookName + ";),?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ps1.setString(2, contactBean.name().gender.name());
            ps1.setString(3,contactBean.name().fullName);
            ps1.setString(4, contactBean.name().petName);
            ps1.setDate(5, new java.sql.Date(contactBean.dateOfBirth().getTime()));
            ps1.setString(6, contactBean.address());
            ps1.executeQuery();
            resultSet = ps1.getGeneratedKeys();
            int contacts_ID = resultSet.getInt("ID");
            return contacts_ID;
    }

    @Override
    public Boolean updateContacts(ContactBean contactBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean deleteContact(String fullName, String phoneBookName) {
        try (Connection connection = getConnection()){
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(
            """
            DELETE FROM contactApp.contacts
            WHERE phoneBook_ID = 
                (SELECT ID 
                 FROM phonebook_master 
                 WHERE name='"""+ phoneBookName+ "')" 
              + "AND fullname=" + fullName + ";");
            int rowsAffected = ps1.executeUpdate();
            System.out.println(rowsAffected + " row(s) has been deleted!");
            connection.commit();
            return true;
        } catch (SQLException e) {
            rollbackSQLCommit(connection);    
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean deleteContactBook(String phoneBookName) {
        try (Connection connection = getConnection()){
            // Give warning that all contacts will also be deleted, delete all contacts first and then delte the contact book
            deleteContact("IS NOT NULL", phoneBookName);
            connection.setAutoCommit(false);
            ps1 = connection.prepareStatement(
            """
            DELETE FROM contactApp.phonebook_master 
            WHERE name(?)""");
            ps1.setString(1, phoneBookName.toUpperCase());
            ps1.execute();
            connection.commit();
            return true;
        } catch (SQLException e) {
            rollbackSQLCommit(connection);    
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public Boolean contactExists(String fullName, String phoneBookName) {
        try (Connection connection = getConnection()){
            ps1 = connection.prepareStatement(
            """
            SELECT fullname
            FROM contactApp.contacts 
            WHERE phoneBookName = ?
              AND fullname = ? ;""");
            ps1.setString(1, phoneBookName.toUpperCase());
            ps1.setString(2, fullName);
            return ps1.execute();
        } catch (SQLException e) {
            rollbackSQLCommit(connection);    
            e.printStackTrace();
            return false;
        }
    }
    
}
