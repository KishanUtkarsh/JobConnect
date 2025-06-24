package com.jobconnect.config.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("dev") // This service will only be active in the 'dev' profile
public class GmailMailService implements MailService{

    private final JavaMailSender javaMailSender;
    public GmailMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
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
    @Async
    public void sendEmail(String[] to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
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
