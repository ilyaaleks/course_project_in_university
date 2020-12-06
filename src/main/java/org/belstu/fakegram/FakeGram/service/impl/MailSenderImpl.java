package org.belstu.fakegram.FakeGram.service.impl;

import lombok.AllArgsConstructor;
import org.belstu.fakegram.FakeGram.service.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderImpl implements MailSender {
    @Value("${spring.mail.username}")
    private String username;
    private JavaMailSender mailSender;

    @Override
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
