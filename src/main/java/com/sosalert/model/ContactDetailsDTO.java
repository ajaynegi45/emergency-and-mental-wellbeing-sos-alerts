package com.sosalert.model;

import java.util.List;

public class ContactDetailsDTO {

    private String userId;
    private List<String> emailAddresses;
    private List<String> phoneNumbers;

    public ContactDetailsDTO() {
    }

    public ContactDetailsDTO(String userId, List<String> emailAddresses, List<String> phoneNumbers) {
        this.userId = userId;
        this.emailAddresses = emailAddresses;
        this.phoneNumbers = phoneNumbers;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public List<String> getEmailAddresses() {
        return emailAddresses;
    }
    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}

