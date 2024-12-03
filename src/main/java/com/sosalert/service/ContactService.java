package com.sosalert.service;

import com.sosalert.exception.InvalidContactException;
import com.sosalert.model.Contact;
import com.sosalert.model.ContactDTO;
import com.sosalert.model.ContactDetailsDTO;
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

            if (userContact.getContacts().stream()
                    .anyMatch(c -> c.getEmail().equals(newContact.getEmail()) ||
                            c.getPhoneNumber().equals(newContact.getPhoneNumber()))) {
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
}
