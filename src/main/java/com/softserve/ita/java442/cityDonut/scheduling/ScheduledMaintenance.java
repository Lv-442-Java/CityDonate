package com.softserve.ita.java442.cityDonut.scheduling;

import com.softserve.ita.java442.cityDonut.constant.ConstantValue;
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

    @Scheduled(fixedDelay = ConstantValue.TASK_POOL_CLEAN_OPERATION_DELAY)
    public void clearSendEmailTaskPool() {
        scheduledTasksPool.clearPool();
    }

}
