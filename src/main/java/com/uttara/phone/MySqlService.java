package main.java.com.uttara.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

public class MySqlService extends IOService {

    PreparedStatement ps1 = null, ps2 = null;
    ResultSet rs = null;
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
            ps1.execute();
            connection.commit();
            return true;
        } catch (SQLException e) {
            rollbackSQLCommit(connection);    
            e.printStackTrace();
            return false;
        }
              
    }

    public static void rollbackSQLCommit(Connection connection)
	{
		if(connection !=null)
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
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
            ps1 = connection.prepareStatement(
                """
                INSERT INTO contactApp.contacts\
                (phonebook_ID, gender, fullname, petname, dateOfBirth, address)\
                VALUES((SELECT ID FROM phonebook.master WHERE ID ="""
                + phoneBookName + ";),?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, phoneBookName);
            ps1.setString(2, contactBean.name().gender.name());
            ps1.setString(3,contactBean.name().fullName);
            ps1.setString(4, contactBean.name().petName);
            ps1.setDate(5, new java.sql.Date(contactBean.dateOfBirth().getTime()));
            ps1.setString(6, contactBean.address());
            ps1.executeQuery();
            rs = ps1.getGeneratedKeys();

            
            connection.commit();
            return true;
        } catch (SQLException se) {
            rollbackSQLCommit(connection);
            se.printStackTrace();
            return false;
        }
    }


    @Override
    public Boolean updateContacts(ContactBean contactBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean deleteContact(String fullName, String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Boolean deleteContactBook(String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Boolean contactBookExists(String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Boolean contactExists(String fullName, String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
