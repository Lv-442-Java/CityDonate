package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.mapper.comment.CommentMapper;
import com.softserve.ita.java442.cityDonut.model.Comment;
import com.softserve.ita.java442.cityDonut.repository.CommentRepository;
import com.softserve.ita.java442.cityDonut.scheduling.ScheduledTaskContainer;
import com.softserve.ita.java442.cityDonut.scheduling.ScheduledTasksPool;
import com.softserve.ita.java442.cityDonut.scheduling.SendEmailNotificationTask;
import com.softserve.ita.java442.cityDonut.service.CommentService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    ScheduledTasksPool scheduledTasksPool;
    @Autowired
    UserService userService;

    @Override
    public List<CommentDto> showComments(long projectId) {
        List<CommentDto> allComments;
        allComments = mapper.convertListToDto(commentRepository.getCommentsByProjectId(projectId));
        return allComments;
    }

    @Override
    public CommentDto sendComment(CommentDto comment, long id) {
        CommentDto newComment = mapper.convertToDto(commentRepository.save(mapper.convertToModel(comment)));
        newComment.setProjectId(id);
        newComment.setDate(LocalDateTime.now());
        return newComment;
    }

    @Override
    public String notifyUsers(long commentId) {
        long[] usersArray = new long[] {3L, 4L};
        Comment comment = commentRepository.getOne(commentId);
        long userId = comment.getUser().getId();
        long projectId = comment.getProject().getId();
        ScheduledTaskContainer taskContainer = scheduledTasksPool.getTask(userId, projectId);
        if (taskContainer == null
                || taskContainer.getScheduledFuture().isDone()
                || taskContainer.getScheduledFuture().isCancelled()) {
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(
                    new SendEmailNotificationTask(usersArray, new ArrayList<String>(){{add(comment.getDescription());}}),
                    new Date(System.currentTimeMillis() + 5000)
            );
            scheduledTasksPool.createTask(userId, projectId, scheduledFuture, comment.getDescription());
        }
        else {
            List<String> messageList = taskContainer.getMessages();
            messageList.add(comment.getDescription());

            if (taskContainer.getScheduledFuture().cancel(false)) {
                ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(
                        new SendEmailNotificationTask(usersArray, messageList),
                        new Date(System.currentTimeMillis() + 5000)
                );
                taskContainer.setScheduledFuture(scheduledFuture);
            }
        }
        return "success";
    }

    @Override
    public String denotifyUsers(long projectId) {
        long userId = userService.getCurrentUser().getId();
        ScheduledTaskContainer taskContainer = scheduledTasksPool.getTask(userId, projectId);
        Map<Long, ScheduledTaskContainer> map = scheduledTasksPool.getMap(userId);
        if (map != null) {
            if (map.size() == 0) {
                scheduledTasksPool.removeUserTasks(userId);
            }
            else {
                if (taskContainer != null) {
                    taskContainer.getScheduledFuture().cancel(false);
                    scheduledTasksPool.removeUserProjectTask(userId, projectId);
                }
                else {
                    scheduledTasksPool.removeUserTasks(userId);
                }
            }
        }

        return "success";
    }
}

