package com.sosalert.service;

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

    public void sendAlertEmails(List<String> emailAddresses, String location) {
        for (String email : emailAddresses) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(email);
                helper.setSubject("Emergency Alert!");
                helper.setText("Emergency reported at location: " + location, true);
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email to: " + email, e);
            }
        }
    }
}

