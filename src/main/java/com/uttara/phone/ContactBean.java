package main.java.com.uttara.phone;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This is a data holder Java 14 record class. This class has instance variables with
 * setter/getters and a convenience constructor to set the state. Since this
 * class has state, we override equals(), hashCode(), toString() so that objects
 * of this class behave correctly if put into collections!
 * 
 * @author mariojoshuaaugustine
 * 
 */
public record ContactBean(Name name,
						  List<String> phoneNumbers,
						  String address,
						  List<String> tags,
						  List<String> email,
						  Date dateOfBirth) 
						  implements Serializable {

	
}

