package com.sosalert.model;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_contacts")
public class UserContact {

    @Id
    private String contactId;

    @NotEmpty
    private String userId;

    private List<Contact> contacts = new ArrayList<>();


    public UserContact() {
    }

    public UserContact(String contactId, String userId, List<Contact> contacts) {
        this.contactId = contactId;
        this.userId = userId;
        this.contacts = contacts;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
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
