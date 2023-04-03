package com.uttara.phone.ioServices;

import com.uttara.phone.ioServices.mySqlService.MySqlService;

/**
 * This class implements the factory pattern and allows us to choose 
 * the default persistence technology.
 * 
 * @author mariojoshuaaugustine
 */

public class IOServiceFactory {

    /**
	 * Persistence methods that can be used 
	 * {@link #PLAIN_TEXT}
     * {@link #SERIALIZED_TEXT}
     * {@link #MYSQL_DATABASE }
	 */
    public enum IOServiceName {
        PLAIN_TEXT,
        SERIALIZED_TEXT,
        MYSQL_DATABASE;
    }

    /**
	 * This method takes in the name of the persistence technology we need
     * and gives us a created instance of it.
	 * 
	 * @param
	 * @return an instance of either PlainTextIOService, 
     * SerializedTextIOService, MySqlService.
	 */
    public IOService getIoService(Enum<?> IOServiceType){		
        if (IOServiceType == IOServiceName.PLAIN_TEXT) {
           return new PlainTextIOService();
           
        } else if(IOServiceType == IOServiceName.SERIALIZED_TEXT) {
           return new SerializedTextIOService();
           
        } else if(IOServiceType == IOServiceName.MYSQL_DATABASE) {
           return new MySqlService();
        } else {
            return null;
        }  
    }
}
