package com.uttara.phone.ioServices.mySqlService;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;

class MySqlContactReader {
    PreparedStatement ps1;
    ResultSet resultSet;

    /**
	 * This method get the joined table from the MySql database, 
     * processes the data using streams and puts it into a 
     * list of ContactBean elements and returns the list 
	 * 
	 * @param 
	 * @return 
	 * ArrayList<ContactBean>
	 */
    List<ContactBean> read(String phoneBookName) {
        //get joined table
        // put each row into a list/map of objects with metadata as key
        //get individual data/list using streams  
        // When scaling, test both approaches using sqlfiddle.com  
        List<ContactBean> contactBeanList = new LinkedList<>();
        List<HashMap<String, Object>> allDataForPhonebook = getAllData(phoneBookName);
        List<Name> nameObjects = extractNames(allDataForPhonebook);
        for(Name name: nameObjects) {
            // Name name = new Name(gender, phoneBookName, phoneBookName);
            List<String> phoneNumbers = extractPhoneNumbers(name.getFullName(), allDataForPhonebook);
            String address = extractAddress(name.getFullName(), allDataForPhonebook);
            List<String> tags = extractTags(name.getFullName(), allDataForPhonebook);
            List<String> email = extractEmailIds(name.getFullName(), allDataForPhonebook);
            Map<String, LocalDateTime> dates = extractDates(name.getFullName(), allDataForPhonebook);
            contactBeanList.add(new ContactBean(phoneBookName, name, phoneNumbers, address, tags, email, dates));
        }
        Logger.getInstance().log("List of contactbeans from db for phonebook :" 
                                        + phoneBookName + "" + contactBeanList );
        return contactBeanList;
    }

    // Add generics to avoid type casting when accessing the object
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
            //Logger.getInstance().log("contactExists method executeQuery() return value = " + resultSet.);
            return getResultSetData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }  
    }

    List<HashMap<String, Object>> getResultSetData(ResultSet resultSet)  throws SQLException {
        List <HashMap<String, Object>> queryList = new LinkedList<>();
        //String key = "";
        HashMap<String, Object> hashMap = null;
        // Save this as an enum instead of a list of Strings, less errors while accessing
        List<String> columnLabelkeys = getResultSetColumnNames(resultSet);
        Map<String, Class<?>> typeMap = resultSet.getStatement().getConnection().getTypeMap();

        while (resultSet.next()) {
            hashMap = new HashMap<String, Object>();
            for(String key: columnLabelkeys) {
                hashMap.put(key, resultSet.getObject(key,  typeMap));
                System.out.println(hashMap + "\n");
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

    List<Name> extractNames(List<HashMap<String,Object>> allDataFromPhonebook) {
        // System.out.println(allDataFromPhonebook.get(1).get("emailid").getClass().getName());
        List<Name> nameObjects
            = allDataFromPhonebook.stream()
            .map(hashmap -> new Name(Gender.valueOf((String)hashmap.get("gender")),
                                    (String)hashmap.get("fullname"),
                                    (String)hashmap.get("petname")))
            .distinct()
            .collect(Collectors.toUnmodifiableList());
            Logger.getInstance().log("nameObject from db = " + nameObjects);                                            
        return nameObjects;
    }

    List<String> extractEmailIds(String fullName, List<HashMap<String, Object>> allDataForPhonebook) {
        List<String> emailIds = allDataForPhonebook.stream()
                .filter(hashmap -> hashmap.get("fullname").equals(fullName))
                .map(hashmap -> (String)hashmap.get("emailid"))
                .distinct()
                .collect(Collectors.toUnmodifiableList());
                Logger.getInstance().log("emaild id's for "+ fullName +" from db" + emailIds);
        return emailIds;
    }

    List<String> extractTags(String fullName, List<HashMap<String, Object>> allDataForPhonebook) {
        List<String> tagsList = allDataForPhonebook.stream()
        .filter(hashmap -> hashmap.get("fullname").equals(fullName))
        .map(hashmap -> (String)hashmap.get("tag"))
        .distinct()
        .collect(Collectors.toUnmodifiableList());
        Logger.getInstance().log("tags for "+ fullName +" from db" + tagsList);
        return tagsList;
    }

    String extractAddress(String fullName, List<HashMap<String, Object>> allDataForPhonebook) {
        Optional<String> address = allDataForPhonebook.stream()
        .filter(hashmap -> hashmap.get("fullname").equals(fullName))
        .map(hashmap -> (String)hashmap.get("address"))
        .distinct()
        .findFirst();
        Logger.getInstance().log("address for "+ fullName +" from db" + address);
    return address.isPresent() ? (String)address.get() : "Address not Available";
    }

    List<String> extractPhoneNumbers(String fullname,List<HashMap<String, Object>> allDataForPhonebook) {
        List<String> phoneNumbers = allDataForPhonebook.stream()
                .filter(hashmap -> hashmap.get("fullname").equals(fullname))
                .map(hashmap -> (String)hashmap.get("phoneNumber"))
                .distinct()
                .collect(Collectors.toUnmodifiableList());
                Logger.getInstance().log("phoneNumbers from db" + phoneNumbers);
        return phoneNumbers;
    }

    Map<String, LocalDateTime> extractDates(String fullName, List<HashMap<String, Object>> allDataForPhonebook) {
        Map<String, LocalDateTime> dateMap = null;
        Optional<HashMap<String,Object>> resultMap = allDataForPhonebook.stream()
                .filter(hashmap -> hashmap.get("fullname").equals(fullName))
                .findFirst();
        if (resultMap.isPresent() ) {
            dateMap = Map.of("dateOfBirth", ((Date)resultMap.get().get("dateOfBirth")).toLocalDate().atStartOfDay(), 
                             "createdDate", (LocalDateTime)resultMap.get().get("createdDate"),
                             "modifiedDate",(LocalDateTime)resultMap.get().get("modifiedDate"));
        }
        Logger.getInstance().log("extract dates method map of dates" + dateMap);      
        return dateMap;
    }


        // get list of contactID's for phoneBookname
    //for each contact ID
        // get list of attributes from contacts tables
        // get list of emails from contacts tables
        // get list of phonenumbers from contacts tables
        // get list of tags
        // put all the attributes in a contacts bean and return
    // List<ContactBean> selectFromContactsTable() {
    //     List <? extends Object> list = new LinkedList<>();
    //     try (Connection connection = MySqlHelper.getConnection()) {
    //         ps1 = connection.prepareStatement(
    //             """
    //             SELECT fullname
    //             FROM contacts
    //             WHERE phonebook_ID = ?
    //                 AND fullname LIKE ? ;""");
    //         ps1.setInt(1, phonebook_ID);
    //         ps1.setString(2, contactBean.name().getFullName());
    //         boolean returnValue = ps1.executeQuery().next();
    //         resultSet.getArray(null)
    //         Logger.getInstance().log("contactExists method executeQuery() return value = " + returnValue);
    //         return returnValue;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return Collections.emptyList();
    //     } 
    // }




}


