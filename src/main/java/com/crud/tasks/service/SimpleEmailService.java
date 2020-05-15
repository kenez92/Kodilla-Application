package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SimpleEmailService {
    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    public void send(final Mail mail) {

        LOGGER.info("Starting mail preparation ! ");
        SimpleMailMessage mailMessage = createMail(mail);
        try {
            javaMailSender.send(mailMessage);
            LOGGER.info("Mail has been sent");
        } catch (MailException e) {
            LOGGER.info("Failed to sent mail:{}", mailMessage);
            LOGGER.info("Exception: {}", e);
        }
    }

    public SimpleMailMessage createMail(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getEmailReceiver());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }


}
