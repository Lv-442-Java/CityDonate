package com.softserve.ita.java442.cityDonut.service;

public interface MailSender {
    void send(String emailTo, String subject, String message);
}
