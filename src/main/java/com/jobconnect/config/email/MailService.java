package com.jobconnect.config.email;

import org.springframework.scheduling.annotation.Async;

public interface MailService {

    @Async
    void sendEmail(String to, String subject, String body);

    @Async
    void sendEmail(String[] to, String subject, String body);

}
