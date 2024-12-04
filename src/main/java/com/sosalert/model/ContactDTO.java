package com.sosalert.model;


public class ContactDTO {
    private String userId;
    private Contact contact;

    @Override
    public String toString() {
        return "ContactDTO{" +
                "userId='" + userId + '\'' +
                ", contact=" + contact +
                '}';
    }

    public ContactDTO() {
    }

    public ContactDTO(String userId, Contact contact) {
        this.userId = userId;
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}

