package com.uttara.phone.ioServices.mySqlService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will containes the mySql database utility methods used by various classes
 * 
 * @author mariojoshuaaugustine
 * @version 1.0
 * @since 2021-12-01
 * 
 */

class MySqlHelper {
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet resultSet = null;
    //Connection connection = null;

    /**
	 * This method creates and returns a new mysql database Connection object
	 * 
	 * @param 
	 * @return 
	 * Connection class instance
	 */
    // See if singleton required and add multithreading abilites
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



    /**
	 * This method either commits or rollsback the sql statement depening on 
     * if the update was successful or not
	 * 
	 * @param 
	 * @return 
	 * true if connection for commited, false if it was rolled back
	 */
    static boolean isUpdateExecutedOrNot(int returnValue, Connection connection) throws SQLException {
        if ( returnValue != 0) {
            connection.commit();
            return true;
        } else {
            rollbackSQLCommit(connection); 
            return false;
        }
    }

    private static void rollbackSQLCommit(Connection connection) throws SQLException {
		if (connection != null) {
				connection.rollback();
		}
	}

    
}
