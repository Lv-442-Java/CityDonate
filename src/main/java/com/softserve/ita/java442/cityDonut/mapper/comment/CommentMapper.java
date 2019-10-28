package com.softserve.ita.java442.cityDonut.mapper.comment;

import com.softserve.ita.java442.cityDonut.dto.comment.CommentDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Comment;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper implements GeneralMapper<Comment, CommentDto> {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProjectRepository projectRepo;


    @Override
    public CommentDto convertToDto(Comment model) {
        return CommentDto.builder().id(model.getId())
                .description(model.getDescription())
                .date(model.getDate())
                .projectId(model.getProject().getId())
                .userId(model.getUser().getId())
                .build();
    }

    @Override
    public Comment convertToModel(CommentDto dto) {
        return Comment.builder().id(dto.getId())
                .date(dto.getDate()).description(dto.getDescription())
                .user(userRepo.getUserById(dto.getUserId()))
                .project(projectRepo.getById(dto.getProjectId()))
                .build();
    }

    public List<CommentDto> convertListToDto(List<Comment> comments) {

        List<CommentDto> dtoList = new ArrayList<>();

        for (Comment comment : comments) {
            dtoList.add(convertToDto(comment));
        }
        return dtoList;
    }
}
