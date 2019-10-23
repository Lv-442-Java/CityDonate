package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.dto.comment.CommentShowDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserForCommentDto;
import com.softserve.ita.java442.cityDonut.model.Comment;
import org.modelmapper.ModelMapper;

public class CommentMapper {

    public static Comment convertToModel(CommentDto dto){
        return Comment.builder().id(dto.getId())
                .date(dto.getDate()).description(dto.getDescription())
                .user(UserForCommentMapper.convertToModel(dto.getUserDto()))
                .build();
    }
}
