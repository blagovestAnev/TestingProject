package com.endava.demo.service;

public interface EmailService {

    /**
     * Sends mail with create message.
     * Sends mail to user, after populating message information.
     *
     * @param to user mail to which to send the mail
     */
    void sendSimpleMessageCreate(String to);

    /**
     * Sends mail whit update message.
     * Sends mail to user, after populating message information.
     *
     * @param to user mail to which to send the mail
     */
    void sendSimpleMessageUpdate(String to);

    /**
     * Sends mail with delete message.
     * Sends mail to user, after populating message information.
     *
     * @param to user mail to which to send the mail
     */
    void sendSimpleMessageDelete(String to);

}
