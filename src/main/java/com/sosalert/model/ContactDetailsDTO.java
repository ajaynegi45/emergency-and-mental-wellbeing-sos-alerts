package com.sosalert.model;

import java.util.List;

public class ContactDetailsDTO {
    private String userId;
    private List<Contact> contacts;

    public ContactDetailsDTO() {
    }

    public ContactDetailsDTO(String userId, List<Contact> contacts) {
        this.userId = userId;
        this.contacts = contacts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
