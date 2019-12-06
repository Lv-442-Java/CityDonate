package com.softserve.ita.java442.cityDonut.scheduling;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Scope("singleton")
@Component
public class ScheduledTasksPool {

    private static Map<Long, Map<Long, ScheduledTaskContainer>> taskPool;

    private ConcurrentHashMap<TaskPoolKey, ScheduledTaskContainer> emailTaskPool;

    static {
        taskPool = new HashMap<>();
    }

    {
        emailTaskPool = new ConcurrentHashMap<>();
    }

    public Map<Long, ScheduledTaskContainer> getMap(long userId) {
        return taskPool.get(userId);
    }

    public ScheduledTaskContainer getTask(long userId, long projectId) {
        Map<Long, ScheduledTaskContainer> map = taskPool.get(userId);
        if (map == null) return null;
        return map.get(projectId);
    }

    public ScheduledTaskContainer getScheduledTask(long userId, long projectId) {
        return emailTaskPool.get(TaskPoolKey.createInstance(userId, projectId));
    }

    public void removeUserTasks(long userId) {
        taskPool.remove(userId);
    }

    public void removeUserProjectTask(long userId, long projectId) {
        taskPool.get(userId).remove(projectId);
        if (taskPool.get(userId).size() == 0) removeUserTasks(userId);
    }

    public void removeTask(long userId, long projectId) {
        emailTaskPool.remove(TaskPoolKey.createInstance(userId, projectId));
    }

    public void createScheduledTask(
            long userId, long projectId, ScheduledFuture<?> scheduledFuture, String text, List<Long> userList) {
        ScheduledTaskContainer scheduledTaskContainer = new ScheduledTaskContainer(text, userList, scheduledFuture);
        emailTaskPool.put(TaskPoolKey.createInstance(userId, projectId), scheduledTaskContainer);
    }

    public void createTask(long userId, long projectId, ScheduledFuture<?> scheduledFuture, String text, List<Long> userList) {
        ScheduledTaskContainer scheduledTaskContainer = new ScheduledTaskContainer(text, userList, scheduledFuture);
        if (taskPool.get(userId) != null) {
            taskPool.get(userId).put(projectId, scheduledTaskContainer);
        }
        else {
            Map<Long, ScheduledTaskContainer> map = new HashMap<>();
            map.put(projectId, scheduledTaskContainer);
            taskPool.put(userId, map);
        }
    }
}
