package com.endava.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class MailConfig {

    private String subjectCreate;
    private String textCreate;
    private String subjectUpdate;
    private String textUpdate;
    private String subjectDelete;
    private String textDelete;

    /**
     * Setup the settings, based on which, the actions confirmation mail is going to be sent towards the clients.
     *
     * @return mailSender settings for the mail, from which the mail is going to be send
     */
    @Bean
    public JavaMailSender emailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("forTestingPurposesOnly55@gmail.com");
        mailSender.setPassword("!q@W3e$r67yU");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
