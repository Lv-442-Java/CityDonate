package com.softserve.ita.java442.cityDonut.scheduling;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledTasksPool {

    private static Map<Long, Map<Long, ScheduledTaskContainer>> taskPool;

    static {
        taskPool = new HashMap<>();
    }

    public Map<Long, ScheduledTaskContainer> getMap(long userId) {
        return taskPool.get(userId);
    }

    public ScheduledTaskContainer getTask(long userId, long projectId) {
        Map<Long, ScheduledTaskContainer> map = taskPool.get(userId);
        if (map == null) return null;
        return map.get(projectId);
    }

    public void removeUserTasks(long userId) {
        taskPool.remove(userId);
    }

    public void removeUserProjectTask(long userId, long projectId) {
        taskPool.get(userId).remove(projectId);
        if (taskPool.get(userId).size() == 0) removeUserTasks(userId);
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
