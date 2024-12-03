package com.sosalert.service;

import com.sosalert.model.Contact;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAlertEmails(List<Contact> contacts, String googleMapsLink) {
        for (Contact contact : contacts) {
            try {
                if (contact.getEmail() != null) {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);

                    helper.setTo(contact.getEmail());
                    helper.setSubject("Emergency Alert!");
                    helper.setText(String.format(
                            "Emergency reported at location: <a href='%s'>View on Google Maps</a>",
                            googleMapsLink), true);

                    mailSender.send(message);
                }
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email to: " + contact.getEmail(), e);
            }
        }
    }
}


