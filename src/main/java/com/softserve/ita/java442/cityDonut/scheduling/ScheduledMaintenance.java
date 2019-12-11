package com.softserve.ita.java442.cityDonut.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class ScheduledMaintenance {

    private ScheduledTasksPool scheduledTasksPool;

    @Autowired
    public ScheduledMaintenance(ScheduledTasksPool scheduledTasksPool) {
        this.scheduledTasksPool = scheduledTasksPool;
    }

    @Scheduled(fixedDelay = 3600000)
    public void clearSendEmailTaskPool() {
        scheduledTasksPool.clearPool();
    }

}
