package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.exception.UserHasNotAccessToCommentException;
import com.softserve.ita.java442.cityDonut.mapper.comment.CommentMapper;
import com.softserve.ita.java442.cityDonut.model.Comment;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.model.Comment;
import com.softserve.ita.java442.cityDonut.repository.CommentRepository;
import com.softserve.ita.java442.cityDonut.scheduling.ScheduledTaskContainer;
import com.softserve.ita.java442.cityDonut.scheduling.ScheduledTasksPool;
import com.softserve.ita.java442.cityDonut.scheduling.SendEmailNotificationTask;
import com.softserve.ita.java442.cityDonut.scheduling.ThreadPoolTaskSchedulerConfig;
import com.softserve.ita.java442.cityDonut.service.CommentService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentMapper mapper;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    ScheduledTasksPool scheduledTasksPool;
    @Autowired
    ApplicationContext context;

    @Override
    public List<CommentDto> showComments(long projectId) {
        List<CommentDto> allComments;
        allComments = mapper.convertListToDto(commentRepository.getCommentsByProjectId(projectId));
        return allComments;
    }

    @Override
    public CommentDto sendComment(CommentDto comment, long id) {
        comment.setProjectId(id);
        comment.setDate(new Timestamp(new Date().getTime()));
        CommentDto newComment = mapper.convertToDto(commentRepository.save(mapper.convertToModel(comment)));
        return newComment;
    }

    @Override
    public CommentDto deleteComment(long commentId) {
        CommentDto commentDto = getCommentIfUserHasAccessToThisComment(commentId);
        commentRepository.deleteById(commentId);
        return commentDto;
    }

    @Override
    public CommentDto editComment(long commentId, CommentDto commentDto) {
        CommentDto oldCommentDto = getCommentIfUserHasAccessToThisComment(commentId);
        oldCommentDto.setDescription(commentDto.getDescription());
        return mapper.convertToDto(commentRepository.save(mapper.convertToModel(oldCommentDto)));
    }

    private CommentDto getCommentIfUserHasAccessToThisComment(long commentId) {
        User user = userService.getCurrentUser();
        Comment comment = commentRepository.findCommentByIdAndUser(commentId, user)
                .orElseThrow(() -> new UserHasNotAccessToCommentException(ErrorMessage.USER_HAS_NOT_ACCESS_TO_COMMENT + commentId));
        return mapper.convertToDto(comment);
    }

    @Override
    public String notifyUsers(long commentId) {
        List<Long> userIdList = new ArrayList<>();
        Map<Long, String> emails = new HashMap<>();
        Comment comment = commentRepository.getOne(commentId);
        long userId = comment.getUser().getId();
        long projectId = comment.getProject().getId();
        List<User> subscribedUsers = comment.getProject().getSubscribedUsers();
        subscribedUsers.forEach((user -> {
            if (user.getId() != userId) {
                userIdList.add(user.getId());
                emails.put(user.getId(), user.getEmail());
            }
        }));
        ScheduledTaskContainer taskContainer = scheduledTasksPool.getScheduledTask(userId, projectId);
        if (taskContainer == null
                || taskContainer.getScheduledFuture().isDone()
                || taskContainer.getScheduledFuture().isCancelled()) {
            SendEmailNotificationTask sendTask = context.getBean(SendEmailNotificationTask.class);
            sendTask.setUserList(userIdList);
            sendTask.setMessageArray(new ArrayList<>(){{add(comment.getDescription());}});
            sendTask.setEmails(emails);
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(
                    sendTask,
                    new Date(System.currentTimeMillis() + ThreadPoolTaskSchedulerConfig.MESSAGE_DELAY_TIME)
            );
            scheduledTasksPool.createScheduledTask(userId, projectId, scheduledFuture, comment.getDescription(), userIdList);
        }
        else {
            List<String> messageList = taskContainer.getMessages();
            messageList.add(comment.getDescription());

            if (taskContainer.getScheduledFuture().cancel(false)) {
                SendEmailNotificationTask sendTask = context.getBean(SendEmailNotificationTask.class);
                sendTask.setUserList(userIdList);
                sendTask.setMessageArray(messageList);
                sendTask.setEmails(emails);
                ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(
                        sendTask,
                        new Date(System.currentTimeMillis() + ThreadPoolTaskSchedulerConfig.MESSAGE_DELAY_TIME)
                );
                taskContainer.setScheduledFuture(scheduledFuture);
            }
        }
        return "success";
    }

    @Override
    public String denotifyUsers(long projectId) {
        long userId = userService.getCurrentUser().getId();
        ScheduledTaskContainer taskContainer = scheduledTasksPool.getScheduledTask(userId, projectId);
        if (taskContainer != null) {
            List<Long> userList = taskContainer.getUserList();
            if (userList == null || userList.size() == 0 || (userList.size() == 1 && userList.get(0) == userId)) {
                taskContainer.getScheduledFuture().cancel(false);
                scheduledTasksPool.removeTask(userId, projectId);
            }
            else if (userList.size() > 1) {
                userList.remove(userId);
            }
        }
        return "success";
    }
}

