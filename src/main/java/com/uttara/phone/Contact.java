package com.uttara.phone;

import java.util.Date;
import java.util.List;

/* 
* @author mariojoshuaaugustine
*/

public class Contact {
    private Name name;
    private List<String> phoneNumbers;
    private String address;
    private List<String> tags;
    private List<String> email;
    private Date dateOfBirth;

    
    public Contact() {
    }
    
    @Override
    public String toString() {
        return "Contact [name=" + name + ", phoneNumbers=" + phoneNumbers + ", address=" + address + ", tags=" + tags
                + ", email=" + email + ", dateOfBirth=" + dateOfBirth + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((phoneNumbers == null) ? 0 : phoneNumbers.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (phoneNumbers == null) {
            if (other.phoneNumbers != null)
                return false;
        } else if (!phoneNumbers.equals(other.phoneNumbers))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        return true;
    }
    public void setName(Name name) {
        this.name = name;
    }
    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public void setEmail(List<String> email) {
        this.email = email;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    

}
