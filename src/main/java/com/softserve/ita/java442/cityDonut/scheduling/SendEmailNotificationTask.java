package com.softserve.ita.java442.cityDonut.scheduling;

import java.util.Date;

public class SendEmailNotificationTask implements Runnable {
    private String message;

    public SendEmailNotificationTask(String message){
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(new Date()+" Runnable Task with "+ message
                +" on thread "+ Thread.currentThread().getName());
    }
}
