package main.java.com.uttara.phone;

public class IOServiceFactory {

    public enum IOServiceName {
        PLAIN_TEXT,
        SERIALIZED_TEXT,
        MYSQL_DATABASE;
    }

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
