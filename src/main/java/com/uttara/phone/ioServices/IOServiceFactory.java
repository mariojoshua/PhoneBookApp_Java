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

    private static IOService ioService = null;

    private IOServiceFactory() {
    }

    /**
	 * This method takes in the name of the persistence technology we need
     * and gives us a created instance of it. 
     * It is implemented as a singleton factory method.
	 * 
	 * @param
	 * @return an instance of either PlainTextIOService, 
     * SerializedTextIOService, MySqlService.
	 */
    public static IOService getIoService (Enum<?> ioServiceType) {		
        if (ioService == null) {
            if (ioServiceType == IOServiceName.PLAIN_TEXT) {
                ioService = new PlainTextIOService();   
            } else if(ioServiceType == IOServiceName.SERIALIZED_TEXT) {
                ioService = new SerializedTextIOService();     
            } else if(ioServiceType == IOServiceName.MYSQL_DATABASE) {
                ioService = new MySqlService();
            } else {
                // throw custom invalid io service exception
                ioService =  null;
            } 
        }
        return ioService; 
    }

}
