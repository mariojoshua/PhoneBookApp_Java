package com.uttara.phone.ioServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;

class MySqlContactReader {
    PreparedStatement ps1;
    ResultSet resultSet;

        // get list of contactID's for phoneBookname
    //for each contact ID
        // get list of attributes from contacts tables
        // get list of emails from contacts tables
        // get list of phonenumbers from contacts tables
        // get list of tags
        // put all the attributes in a contacts bean and return

    //OR
        //get joined table
        // put each row into a list/map of objects with metadata as key
        //get individual data/list using streams  
    // When scaling test both approaches using sqlfiddle.com  
    List<ContactBean> read(String phoneBookName) {
        List<HashMap<String,Object>> allDataForPhonebook= getAllData(phoneBookName);
        return Collections.emptyList();
    }

    List<HashMap<String,Object>> getAllData(String phoneBookName) {
        try (Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement("""
                SELECT name AS phonebookName, gender, fullname, petname, address, emailid , phoneNumber, tag, dateOfBirth, createdDate, modifiedDate 
                FROM contactApp.phonebook_master as pm
                JOIN contactApp.contacts as ct
                ON pm.id = ct.phonebook_ID
                JOIN contactApp.email as em
                ON ct.id = em.contacts_ID
                JOIN contactApp.phonenumber as pn
                ON ct.id = pn.contacts_ID
                JOIN contactApp.contacts_tags as ctt
                ON ct.id = ctt.contacts_ID
                JOIN contactApp.tags as tg
                ON ctt.tag_ID = tg.ID
                WHERE pm.name = ?;""");
            ps1.setString(1, phoneBookName);
            //boolean returnValue = ps1.executeQuery().next();
            resultSet = ps1.executeQuery();
            System.out.println(connection.getTypeMap());
            //Logger.getInstance().log("contactExists method executeQuery() return value = " + returnValue);
            return getResultSetData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }  
    }

    List<HashMap<String,Object>> getResultSetData(ResultSet resultSet)  throws SQLException {
        List <HashMap<String,Object>> queryList = new LinkedList<>();
        //String key = "";
        HashMap<String,Object> hashMap = null;
        List<String> columnLabelkeys = new LinkedList<>();
        columnLabelkeys = getResultSetColumnNames(resultSet);
        Map<String, Class<?>> typeMap = resultSet.getStatement().getConnection().getTypeMap();

        while (resultSet.next()) {
            hashMap = new HashMap<String,Object>();
            for(String key: columnLabelkeys) {
                hashMap.put(key, resultSet.getObject(key,  typeMap));
                //System.out.println(hashMap + "\n");
                
            }
            queryList.add(hashMap);
            
        }
        Logger.getInstance().log("queryList.size() =" +queryList.size());
        return queryList;
    }

    List<String> getResultSetColumnNames(ResultSet resultSet) throws SQLException {
        List<String> columnNames = new LinkedList<>();
        int resultSetSize = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= resultSetSize; i++) {
            columnNames.add(resultSet.getMetaData().getColumnLabel(i));
        }
        //System.out.println(columnNames);
        Logger.getInstance().log("Result set column names = " + columnNames);
        return columnNames;
    }

    

    List<ContactBean> selectFromContactsTable() {
        List <? extends Object> list = new LinkedList<>();
        try (Connection connection = MySqlHelper.getConnection()) {
            ps1 = connection.prepareStatement(
                """
                SELECT fullname
                FROM contacts
                WHERE phonebook_ID = ?
                    AND fullname LIKE ? ;""");
            ps1.setInt(1, phonebook_ID);
            ps1.setString(2, contactBean.name().getFullName());
            boolean returnValue = ps1.executeQuery().next();
            resultSet.getArray(null)
            Logger.getInstance().log("contactExists method executeQuery() return value = " + returnValue);
            return returnValue;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } 
    }

    List<String> selectFromEmailTable() {
        return Collections.emptyList();
    }

    List<String> selectFromPhoneNumberTable() {
        return Collections.emptyList();
    }

    List<String> selectFromTabsTable() {
        return Collections.emptyList();
    }




}


