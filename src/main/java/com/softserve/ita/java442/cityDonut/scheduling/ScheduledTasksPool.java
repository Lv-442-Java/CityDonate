package com.softserve.ita.java442.cityDonut.scheduling;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Scope("singleton")
@Component
public class ScheduledTasksPool {

    private ConcurrentHashMap<TaskPoolKey, ScheduledTaskContainer> emailTaskPool;

    {
        emailTaskPool = new ConcurrentHashMap<>();
    }

    public ScheduledTaskContainer getScheduledTask(long userId, long projectId) {
        return emailTaskPool.get(TaskPoolKey.createInstance(userId, projectId));
    }

    public void removeTask(long userId, long projectId) {
        emailTaskPool.remove(TaskPoolKey.createInstance(userId, projectId));
    }

    public void createScheduledTask(
            long userId, long projectId, ScheduledFuture<?> scheduledFuture, String text, List<Long> userList) {
        ScheduledTaskContainer scheduledTaskContainer = new ScheduledTaskContainer(text, userList, scheduledFuture);
        emailTaskPool.put(TaskPoolKey.createInstance(userId, projectId), scheduledTaskContainer);
    }

}
