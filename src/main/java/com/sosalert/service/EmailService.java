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

    public void sendAlertEmails(List<Contact> contacts, String googleMapsLink, String username) {
        for (Contact contact : contacts) {
            try {
                if (contact.getEmail() != null) {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);

                    helper.setTo(contact.getEmail());
                    helper.setSubject("🚨 URGENT: Emergency Alert! 🚨");
                    helper.setText(
                            String.format(
                                    "<html>" +
                                            "<body>" +
                                            "<h2 style='color: red;'>Emergency Alert!</h2>" +
                                            "<p><strong>%s</strong> has reported an emergency and may need immediate assistance.</p>" +
                                            "<p><b>Location:</b> <a href='%s'>Click here to view on Google Maps</a></p>" +
                                            "<p>Please act promptly to ensure their safety.</p>" +
                                            "<p style='color: gray;'>This alert was automatically generated. For more details, please contact the individual directly.</p>" +
                                            "</body>" +
                                            "</html>",
                                    username, googleMapsLink
                            ),
                            true
                    );

                    mailSender.send(message);
                }
            } catch (MessagingException e) {
                throw new RuntimeException("Failed to send email to: " + contact.getEmail(), e);
            }
        }
    }

}







