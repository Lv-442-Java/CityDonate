package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import java.util.List;

public interface CommentService {

    List<CommentDto> showComments(long projectId);
    CommentDto sendComment(CommentDto comment, long id);

    CommentDto deleteComment(long commentId);

    CommentDto editComment(long commentId, CommentDto commentDto);
}
