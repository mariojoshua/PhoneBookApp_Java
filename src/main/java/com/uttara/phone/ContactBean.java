package com.uttara.mvc.contactsApp;

import java.util.Objects;

/**
 * This is a data holder class. This class has instance variables with
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

