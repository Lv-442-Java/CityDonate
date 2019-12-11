package com.softserve.ita.java442.cityDonut.scheduling;

import com.softserve.ita.java442.cityDonut.service.impl.MailSenderImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailNotificationTask implements Runnable {
    private List<Long> userList;
    private Map<Long, String> emails;
    private List<String> messageArray;
    MailSenderImpl mailSender;

    @Autowired
    public SendEmailNotificationTask(MailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public SendEmailNotificationTask(List<Long> userList, List<String> messageArray, Map<Long, String> emails) {
        this.userList = userList;
        this.messageArray = messageArray;
        this.emails = emails;
    }

    @Override
    public void run() {

        StringBuilder message = new StringBuilder();
        message.append("Dear user.\n").append("You have unread messages!\n");

        messageArray.forEach((messageStr) -> message
                .append(" --- ")
                .append(messageStr)
                .append(" --- \n"));

        String builtMessage = message.toString();

        for (int i = 0; i < userList.size(); i++) {
            try {
                String email = emails.get(userList.get(i));
                mailSender.send(email, "Unread messages", builtMessage);}
            catch (Exception exc) {exc.printStackTrace();}
        }

        System.out.println(new Date()+" Runnable Task with "
                +" on thread "+ Thread.currentThread().getName());
        System.out.println("Send email to users: " + Arrays.toString(userList.toArray()));
        System.out.println("Messages: " + Arrays.toString(messageArray.toArray()));
    }
}
