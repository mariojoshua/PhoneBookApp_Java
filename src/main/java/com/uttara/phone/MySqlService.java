package main.java.com.uttara.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MySqlService implements IOService {


    PreparedStatement ps1 = null, ps2 = null;
    ResultSet rs = null;
 

    public static Connection getConnection() {
		String URL = "jdbc:mysql://localhost:3306/";
		String UID = "root";
		String PASS = "";
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		try {
            Connection con = DriverManager.getConnection(URL,UID,PASS);
			Class.forName(DRIVER);
			System.out.println("Established Connection Succesfully "+con);
			return con;
		} catch (ClassNotFoundException |SQLException se) {
			se.printStackTrace();
		}
		return null;
    }

    @Override
    public Boolean createContactBook(String phoneBookName) {
        try (Connection connection = getConnection()){
            ps1 = connection.prepareStatement
            ("INSERT INTO contactApp.phonebook_master" 
            + "(name)" 
            + "VALUES(?)");
            ps1.setString(1, phoneBookName.toUpperCase());
            ps1.execute();
            return true;
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
    public Boolean writeContacts(ContactBean contactBean, String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
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
