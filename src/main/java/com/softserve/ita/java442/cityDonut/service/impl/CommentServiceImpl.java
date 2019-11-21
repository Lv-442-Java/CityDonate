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
import com.softserve.ita.java442.cityDonut.service.CommentService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

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
        comment.setDate(LocalDateTime.now());
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
        //userIdList.add(2L);
        //userIdList.add(3L);
        //userIdList.add(4L);
        Comment comment = commentRepository.getOne(commentId);
        long userId = comment.getUser().getId();
        long projectId = comment.getProject().getId();
        List<User> subscribedUsers = comment.getProject().getSubscribedUsers();
        System.out.println(Arrays.toString(subscribedUsers.toArray()));
        System.out.println(subscribedUsers);
        subscribedUsers.forEach((user -> {userIdList.add(user.getId());emails.put(user.getId(), user.getEmail());}));
        System.out.println(userIdList);

        ScheduledTaskContainer taskContainer = scheduledTasksPool.getTask(userId, projectId);
        if (taskContainer == null
                || taskContainer.getScheduledFuture().isDone()
                || taskContainer.getScheduledFuture().isCancelled()) {
            SendEmailNotificationTask sendTask = context.getBean(SendEmailNotificationTask.class);
            sendTask.setUserList(userIdList);
            sendTask.setMessageArray(new ArrayList<String>(){{add(comment.getDescription());}});
            sendTask.setEmails(emails);
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(
                    sendTask,
                    new Date(System.currentTimeMillis() + 5000)
            );
            scheduledTasksPool.createTask(userId, projectId, scheduledFuture, comment.getDescription(), userIdList);
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
                    List<Long> userList = taskContainer.getUserList();
                    if (userList == null || userList.size() == 0 || (userList.size() == 1 && userList.get(0) == userId)) {
                        taskContainer.getScheduledFuture().cancel(false);
                        scheduledTasksPool.removeUserProjectTask(userId, projectId);
                    }
                    else if (userList.size() > 1) {
                        if (userList.contains(userId)) {userList.remove(userId);}
                    }
                }
                else {
                    scheduledTasksPool.removeUserTasks(userId);
                }
            }
        }

        return "success";
    }
}

