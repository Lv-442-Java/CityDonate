package com.softserve.ita.java442.cityDonut.scheduling;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class TaskPoolKey {

    private long userId;

    private long projectId;

    static TaskPoolKey createInstance(long userId, long projectId) {
        TaskPoolKey result = new TaskPoolKey();
        result.userId = userId;
        result.projectId = projectId;
        return result;
    }

}
