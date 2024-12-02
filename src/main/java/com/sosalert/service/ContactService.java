package com.sosalert.service;

import com.sosalert.exception.InvalidContactException;
import com.sosalert.exception.UserNotFoundException;
import com.sosalert.model.ContactDTO;
import com.sosalert.model.UserContact;
import com.sosalert.repository.UserContactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ContactService {

    private final UserContactRepository contactRepository;

    public ContactService(UserContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void saveContact(ContactDTO contactDTO) {
        // Find the user by userId or create a new UserContact if none exists
        UserContact userContact = contactRepository.findByUserId(contactDTO.getUserId())
                .orElseGet(() -> {
                    UserContact newUserContact = new UserContact();
                    newUserContact.setUserId(contactDTO.getUserId());
                    newUserContact.setEmailAddresses(new ArrayList<>()); // Initialize email list
                    newUserContact.setPhoneNumbers(new ArrayList<>());   // Initialize phone list
                    return newUserContact;
                });

        // Validate input: At least one contact detail (email or phone) must be provided
        if ((contactDTO.getEmail() == null || contactDTO.getEmail().isBlank())
                && (contactDTO.getPhoneNumber() == null || contactDTO.getPhoneNumber().isBlank())) {
            throw new InvalidContactException("At least one contact detail (email or phone number) is required.");
        }

        // Add email if provided and not already present
        if (contactDTO.getEmail() != null && !contactDTO.getEmail().isBlank()) {
            if (userContact.getEmailAddresses().contains(contactDTO.getEmail())) {
                throw new InvalidContactException("Email already exists.");
            }
            userContact.getEmailAddresses().add(contactDTO.getEmail());
        }

        // Add phone number if provided and not already present
        if (contactDTO.getPhoneNumber() != null && !contactDTO.getPhoneNumber().isBlank()) {
            if (userContact.getPhoneNumbers().contains(contactDTO.getPhoneNumber())) {
                throw new InvalidContactException("Phone number already exists.");
            }
            userContact.getPhoneNumbers().add(contactDTO.getPhoneNumber());
        }

        // Save or update the user contact in the database
        contactRepository.save(userContact);
    }
}
