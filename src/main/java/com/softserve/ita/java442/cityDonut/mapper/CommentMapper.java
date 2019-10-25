package com.softserve.ita.java442.cityDonut.mapper;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper implements GeneralMapper<Comment, CommentDto> {

    @Autowired
    private ProjectForCommentMapper projectMapper;
    @Autowired
    private UserForCommentMapper userMapper;

    @Override
    public  CommentDto convertToDto(Comment model) {
        return CommentDto.builder().id(model.getId())
                .description(model.getDescription())
                .date(model.getDate())
                .projectDto(projectMapper.convertToDto(model.getProject()))
                .userDto(userMapper.convertToDto(model.getUser()))
                .build();
    }

    @Override
    public  Comment convertToModel(CommentDto dto){
        return Comment.builder().id(dto.getId())
                .date(dto.getDate()).description(dto.getDescription())
                .user(userMapper.convertToModel(dto.getUserDto()))
               .project(projectMapper.convertToModel(dto.getProjectDto()))
                .build();
    }

    public List<CommentDto> convertListToDto(List<Comment> comments){

        List<CommentDto> dtoList =  new ArrayList<>();

        for (Comment comment : comments ) {
            dtoList.add(convertToDto(comment));
        }
        return dtoList;
    }
}
