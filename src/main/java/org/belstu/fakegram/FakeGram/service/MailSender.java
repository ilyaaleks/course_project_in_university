package org.belstu.fakegram.FakeGram.service;

public interface MailSender {
   void send(String emailTo, String subject, String message);
}
