package com.jobconnect.config.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("prod") // This service will only be active in the 'prod' profile
public class MailTrapMailService implements MailService{

    private final JavaMailSender javaMailSender;
    private String SENDER_EMAIL = "support@demomailtrap.co";
    public MailTrapMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        // Simulate sending an email in production
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(SENDER_EMAIL);
        message.setSubject(subject);
        message.setText(body);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public void sendEmail(String[] to, String subject, String body) {
        // Simulate sending an email in production
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(SENDER_EMAIL);
        message.setSubject(subject);
        message.setText(body);
        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            log.error("Failed to send email to {}: {}", String.join(", ", to), e.getMessage());
        }
    }
}
