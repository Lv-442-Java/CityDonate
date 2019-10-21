package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.CommentDto;
import com.softserve.ita.java442.cityDonut.model.Comment;
import org.modelmapper.ModelMapper;

public class CommentMapper {
    public static CommentDto convertToDto(Comment comment) {
        return new ModelMapper().map(comment, CommentDto.class);
    }

    public static Comment convertToModel(CommentDto commentDto) {
        return new ModelMapper().map(commentDto, Comment.class);
    }
}
