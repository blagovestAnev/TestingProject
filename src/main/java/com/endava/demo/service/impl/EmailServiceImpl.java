package com.endava.demo.service.impl;

import com.endava.demo.config.MailConfig;
import com.endava.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final MailConfig mailConfig;

    public void sendSimpleMessageCreate(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(this.mailConfig.getSubjectCreate());
        message.setText(this.mailConfig.getTextCreate() + "");
        emailSender.send(message);
    }

    public void sendSimpleMessageUpdate(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(this.mailConfig.getSubjectUpdate());
        message.setText(this.mailConfig.getTextUpdate() + "");
        emailSender.send(message);
    }
}
