package com.jobconnect.config.email;

public interface MailService {
    void sendEmail(String to, String subject, String body);

    void sendEmail(String[] to, String subject, String body);

}
