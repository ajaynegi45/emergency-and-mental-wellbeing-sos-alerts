package com.sosalert.service;

import com.sosalert.exception.InvalidContactException;
import com.sosalert.model.Contact;
import com.sosalert.model.ContactDTO;
import com.sosalert.model.ContactDetailsDTO;
import com.sosalert.model.UserContact;
import com.sosalert.repository.UserContactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@Service
public class ContactService {

    private final UserContactRepository contactRepository;
    public ContactService(UserContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void saveContact(ContactDTO contactDTO) {
        try {
            UserContact userContact = contactRepository.findByUserId(contactDTO.getUserId())
                    .orElseGet(() -> {
                        UserContact newUserContact = new UserContact();
                        newUserContact.setUserId(contactDTO.getUserId());
                        newUserContact.setContacts(new ArrayList<>());
                        return newUserContact;
                    });

            if (contactDTO.getContact() == null) {
                throw new InvalidContactException("Contact details cannot be null.");
            }

            Contact newContact = contactDTO.getContact();
            if (newContact.getEmail() == null && newContact.getPhoneNumber() == null) {
                throw new InvalidContactException("At least one contact detail (email or phone number) is required.");
            }

            // Check for existing contacts (null-safe)
            if (userContact.getContacts().stream()
                    .anyMatch(c -> Objects.equals(c.getEmail(), newContact.getEmail()) ||
                            Objects.equals(c.getPhoneNumber(), newContact.getPhoneNumber()))) {
                throw new InvalidContactException("Contact already exists.");
            }

            userContact.getContacts().add(newContact);
            contactRepository.save(userContact);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save contact: " + e.getMessage());
        }
    }


    public ContactDetailsDTO getContactDetails(String userId) {
        UserContact userContact = contactRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidContactException("User not found with ID: " + userId));

        ContactDetailsDTO contactDetailsDTO = new ContactDetailsDTO();
        contactDetailsDTO.setUserId(userContact.getUserId());
        contactDetailsDTO.setContacts(userContact.getContacts());
        return contactDetailsDTO;
    }




    public boolean deleteContact(String userId, String email, String phoneNumber) {
        // Fetch the user's contact list or throw an exception if the user is not found
        UserContact userContact = contactRepository.findByUserId(userId)
                .orElseThrow(() -> new InvalidContactException("User not found with ID: " + userId));

        boolean isDeleted = false;

        // Remove the contact if either the email or phone number matches
        if ((email != null && !email.isBlank()) || (phoneNumber != null && !phoneNumber.isBlank())) {
            Iterator<Contact> iterator = userContact.getContacts().iterator();
            while (iterator.hasNext()) {
                Contact contact = iterator.next();
                if ((email != null && email.equals(contact.getEmail())) ||
                        (phoneNumber != null && phoneNumber.equals(contact.getPhoneNumber()))) {
                    iterator.remove();
                    isDeleted = true;
                }
            }
        } else {
            throw new InvalidContactException("Either email or phone number must be provided for deletion.");
        }

        if (isDeleted) {
            contactRepository.save(userContact); // Save the updated contact list
        }

        return isDeleted;
    }

}
