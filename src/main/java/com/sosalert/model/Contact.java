package com.sosalert.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Contact {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @Email(message = "Invalid email address format.")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits.")
    private String phoneNumber;


    public Contact() {
    }

    public Contact(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
