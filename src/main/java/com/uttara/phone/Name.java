package main.java.com.uttara.phone;

import java.io.Serializable;

public class Name implements Serializable{
    Gender gender;
    String fullName;
    String petName;

    public Gender getGender() {
        return gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPetName() {
        return petName;
    }

    public enum Gender {
        M,
        F,
        O;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Name(Gender gender, String fullName, String petName) {
        this.gender = gender;
        this.fullName = fullName;
        this.petName = petName;
    }

    public Name() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        result = prime * result + ((petName == null) ? 0 : petName.hashCode());
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
        Name other = (Name) obj;
        if (gender != other.gender)
            return false;
        if (fullName == null) {
            if (other.fullName != null)
                return false;
        } else if (!fullName.equals(other.fullName))
            return false;
        if (petName == null) {
            if (other.petName != null)
                return false;
        } else if (!petName.equals(other.petName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Name [gender=" + gender + ", fullName=" + fullName + ", petName=" + petName + "]";
    }

    
}
