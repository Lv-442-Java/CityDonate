package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.mapper.comment.CommentMapper;
import com.softserve.ita.java442.cityDonut.repository.CommentRepository;
import com.softserve.ita.java442.cityDonut.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper mapper;

    @Override
    public List<CommentDto> showComments(long projectId) {

        List<CommentDto> allComments;

        allComments = mapper.convertListToDto(commentRepository.getCommentsByProjectId(projectId));

        return allComments;
    }

    @Override
    public CommentDto sendComment(CommentDto comment) {

        CommentDto newComment;

        newComment = mapper.convertToDto(commentRepository.save(mapper.convertToModel(comment)));

        return newComment;
    }
}

