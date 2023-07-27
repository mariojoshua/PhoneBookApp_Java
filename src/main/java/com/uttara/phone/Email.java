package com.uttara.phone;

/**
 * @hidden
 * @deprecated
 */

public class Email {
    EmailType emailType;
    String emailID;

    enum EmailType {
        PERSONAL,
        WORK;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((emailType == null) ? 0 : emailType.hashCode());
        result = prime * result + ((emailID == null) ? 0 : emailID.hashCode());
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
        Email other = (Email) obj;
        if (emailType != other.emailType)
            return false;
        if (emailID == null) {
            if (other.emailID != null)
                return false;
        } else if (!emailID.equals(other.emailID))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Email [emailType=" + emailType + ", emailID=" + emailID + "]";
    }
    
}
