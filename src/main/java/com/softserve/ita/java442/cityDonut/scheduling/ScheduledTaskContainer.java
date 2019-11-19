package com.softserve.ita.java442.cityDonut.scheduling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledTaskContainer {

    private List<String> messages;

    private ScheduledFuture<?> scheduledFuture;

    public ScheduledTaskContainer(String text, ScheduledFuture<?> scheduledFuture) {
        this.messages = new ArrayList<>();
        messages.add(text);
        this.scheduledFuture = scheduledFuture;
    }

}
