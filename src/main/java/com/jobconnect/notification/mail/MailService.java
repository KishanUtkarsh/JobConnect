package com.jobconnect.notification.mail;

import org.springframework.scheduling.annotation.Async;

public interface MailService {

    @Async
    void sendEmail(String to, String subject, String body);

    @Async
    void sendEmail(String[] to, String subject, String body);

}
