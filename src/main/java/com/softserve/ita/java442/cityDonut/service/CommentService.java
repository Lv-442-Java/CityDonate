package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentShowDto;

import java.util.List;

public interface CommentService {

    List<CommentShowDto> showComments(long projectId);

}
