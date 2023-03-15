package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.uttara.phone.Logger;

class MySqlHelper {
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    //Connection connection = null;

    static Connection getConnection() {
		String URL = "jdbc:mysql://localhost:3306/";
		String UID = "root";
		String PASS = "";
		String DRIVER ="com.mysql.cj.jdbc.Driver";
		try {
            Connection connection = DriverManager.getConnection(URL,UID,PASS);
			Class.forName(DRIVER);
			//Logger.getInstance().log("Established Connection Succesfully "+ connection);
			connection.prepareStatement("USE contactAPP").execute();
            return connection;
		} catch (ClassNotFoundException |SQLException se) {
            se.printStackTrace();
		}
		return null;
    }

    static void rollbackSQLCommit(Connection connection) throws SQLException {
		if (connection != null) {
				connection.rollback();
		}
	}

    static boolean isUpdateExecutedOrNot(int returnValue, Connection connection) throws SQLException {
        if ( returnValue != 0) {
            connection.commit();
            return true;
        } else {
            rollbackSQLCommit(connection); 
            return false;
        }
    }

    
}
