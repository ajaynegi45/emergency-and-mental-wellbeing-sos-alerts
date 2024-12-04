package com.sosalert.controller;

import com.sosalert.exception.InvalidContactException;
import com.sosalert.model.ContactDTO;
import com.sosalert.model.ContactDetailsDTO;
import com.sosalert.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin("*")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/add-contacts")
    public ResponseEntity<String> addContacts(@RequestBody @Validated ContactDTO contactDTO) {
        try {
            System.out.println(contactDTO.toString());
            if (contactDTO.getUserId() == null || contactDTO.getUserId().isBlank()) {
                throw new InvalidContactException("User ID is required to add contact.");
            }
            contactService.saveContact(contactDTO);
            return ResponseEntity.ok("Contact saved successfully!");
        } catch (InvalidContactException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save contact: " + e.getMessage());
        }
    }

    @GetMapping("/get-contacts/{userId}")
    public ResponseEntity<?> getContacts(@PathVariable String userId) {
        try {
            if (userId == null || userId.isBlank()) {
                throw new InvalidContactException("User ID is required to fetch contacts.");
            }
            ContactDetailsDTO contactDetails = contactService.getContactDetails(userId);
            return ResponseEntity.ok(contactDetails);
        } catch (InvalidContactException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch contacts: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete-contact/{userId}")
    public ResponseEntity<String> deleteContact(
            @PathVariable String userId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {
        if (userId == null || userId.isBlank()) {
            throw new InvalidContactException("User ID is required to delete contact.");
        }
        if ((email == null || email.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
            throw new InvalidContactException("Either email or phone number must be provided for deletion.");
        }

        boolean isDeleted = contactService.deleteContact(userId, email, phoneNumber);
        if (isDeleted) {
            return ResponseEntity.ok("Contact deleted successfully!");
        } else {
            return ResponseEntity.status(404).body("Contact not found.");
        }
    }
}

