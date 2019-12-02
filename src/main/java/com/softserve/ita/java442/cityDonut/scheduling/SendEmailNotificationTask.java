package com.softserve.ita.java442.cityDonut.scheduling;

import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import com.softserve.ita.java442.cityDonut.service.impl.MailSenderImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
    @Autowired
    MailSenderImpl mailSender;

    public SendEmailNotificationTask(List<Long> userList, List<String> messageArray, Map<Long, String> emails) {
        this.userList = userList;
        this.messageArray = messageArray;
        this.emails = emails;
        //mailSender = new MailSenderImpl();
        //mailSender.setMailSender(new JavaMailSenderImpl());
    }

    @Override
    public void run() {
        StringBuilder message = new StringBuilder("Добрий день!\n Ви маєте непрочитані повідомлення!\n");
        messageArray.forEach((messageStr) -> {
            message.append("#----------------\n");
            message.append(messageStr);
            message.append("\n#----------------\n");
        });
        String builtMessage = message.toString();


        userList.forEach((user) -> {
            try {
                //System.out.println(userRepository);
                System.out.println(mailSender);
                String email = emails.get(user);
                mailSender.send(email, "Unread messages", builtMessage);}
            catch (Exception exc) {exc.printStackTrace();}
        });

        System.out.println(new Date()+" Runnable Task with "
                +" on thread "+ Thread.currentThread().getName());
        System.out.println(Arrays.toString(userList.toArray()));
        System.out.println(Arrays.toString(messageArray.toArray()));
    }
}
