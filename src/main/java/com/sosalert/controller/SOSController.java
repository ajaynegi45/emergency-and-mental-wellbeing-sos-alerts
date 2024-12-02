package com.sosalert.controller;

import com.sosalert.model.UserContact;
import com.sosalert.repository.UserContactRepository;
import com.sosalert.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/sos")
public class SOSController {

    private final UserContactRepository contactRepository;
    private final EmailService emailService;

    public SOSController(UserContactRepository contactRepository, EmailService emailService) {
        this.contactRepository = contactRepository;
        this.emailService = emailService;
    }

    @PostMapping("/alert")
    public ResponseEntity<String> sendSOSAlert(@RequestParam String userId, @RequestParam String location) {
        Optional<UserContact> userContact = contactRepository.findByUserId(userId);

        if (userContact.isPresent()) {
            UserContact contact = userContact.get();
            emailService.sendAlertEmails(contact.getEmailAddresses(), location);
            return ResponseEntity.ok("SOS alerts sent successfully!");
        } else {
            return ResponseEntity.status(404).body("User not found.");
        }
    }
}
