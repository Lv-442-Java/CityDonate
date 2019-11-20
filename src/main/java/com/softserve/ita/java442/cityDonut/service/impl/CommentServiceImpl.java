package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.exception.UserHasNotAccessToCommentException;
import com.softserve.ita.java442.cityDonut.mapper.comment.CommentMapper;
import com.softserve.ita.java442.cityDonut.model.Comment;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.CommentRepository;
import com.softserve.ita.java442.cityDonut.service.CommentService;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
}

