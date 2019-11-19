package com.softserve.ita.java442.cityDonut.scheduling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailNotificationTask implements Runnable {
    private long[] userArray;
    private List<String> messageArray;

    @Override
    public void run() {
        System.out.println(new Date()+" Runnable Task with "
                +" on thread "+ Thread.currentThread().getName());
        System.out.println(Arrays.toString(userArray));
        System.out.println(Arrays.toString(messageArray.toArray()));
    }
}
