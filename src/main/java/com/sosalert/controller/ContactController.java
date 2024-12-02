package com.sosalert.controller;

import com.sosalert.exception.InvalidContactException;
import com.sosalert.model.ContactDTO;
import com.sosalert.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sos")
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
}
