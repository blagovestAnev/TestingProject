package com.endava.demo.service.impl;

import com.endava.demo.config.MailConfig;
import com.endava.demo.service.EmailService;
import com.google.common.base.Strings;
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

    @Override
    public void sendSimpleMessageCreate(String to) {
        SimpleMailMessage message = mailInformation(to, this.mailConfig.getSubjectCreate(), Strings.nullToEmpty(this.mailConfig.getTextCreate()));
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMessageUpdate(String to) {
        SimpleMailMessage message = mailInformation(to, this.mailConfig.getSubjectUpdate(), Strings.nullToEmpty(this.mailConfig.getTextUpdate()));
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMessageDelete(String to) {
        SimpleMailMessage message = mailInformation(to, this.mailConfig.getSubjectDelete(), Strings.nullToEmpty(this.mailConfig.getTextDelete()));
        emailSender.send(message);
    }

    /**
     * Mail message properties insert.
     * Populate the mail message with all the necessary properties for standard mail.
     *
     * @param to      the mail of the user, to whom the message will be sent
     * @param subject the headline of the message
     * @param text    the body of the message
     * @return        object containing message properties
     */
    private SimpleMailMessage mailInformation(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }

}
