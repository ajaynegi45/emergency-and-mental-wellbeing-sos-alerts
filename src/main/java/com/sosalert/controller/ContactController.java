package com.sosalert.controller;

import com.sosalert.exception.InvalidContactException;
import com.sosalert.model.ContactDTO;
import com.sosalert.model.ContactDetailsDTO;
import com.sosalert.service.ContactService;
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
        if (contactDTO.getUserId() == null || contactDTO.getUserId().isBlank()) {
            throw new InvalidContactException("User ID is required to add contact.");
        }
        contactService.saveContact(contactDTO);
        return ResponseEntity.ok("Contact saved successfully!");
    }


    // New method to fetch all email and phone numbers for a given userId
    @GetMapping("/get-contacts/{userId}")
    public ResponseEntity<ContactDetailsDTO> getContacts(@PathVariable String userId) {
        if (userId == null || userId.isBlank()) {
            throw new InvalidContactException("User ID is required to fetch contacts.");
        }
        ContactDetailsDTO contactDetails = contactService.getContactDetails(userId);
        return ResponseEntity.ok(contactDetails);
    }
}
