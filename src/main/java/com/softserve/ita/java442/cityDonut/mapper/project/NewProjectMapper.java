package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.NewProjectDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryNameMapper;
import com.softserve.ita.java442.cityDonut.mapper.gallery.GalleryMapper;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewProjectMapper implements GeneralMapper<Project, NewProjectDto> {

    @Autowired
    private CategoryNameMapper categoryNameMapper;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private GalleryMapper galleryMapper;

    @Override
    public NewProjectDto convertToDto(Project project) {
        return NewProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .location(project.getLocation())
                .locationLatitude(project.getLocationLatitude())
                .locationLongitude(project.getLocationLongitude())
                .moneyNeeded(project.getMoneyNeeded())
                .galleryDto(galleryMapper.convertToDto(projectRepository.getOne(project.getId()).getGallery()))
                .categories(categoryNameMapper.convertListToDto(project.getCategories()))
                .build();
    }

    @Override
    public Project convertToModel(NewProjectDto projectDTO) {
        return Project.builder()
                .id(projectDTO.getId())
                .name(projectDTO.getName())
                .description(projectDTO.getDescription())
                .location(projectDTO.getLocation())
                .locationLatitude(projectDTO.getLocationLatitude())
                .locationLongitude(projectDTO.getLocationLongitude())
                .moneyNeeded(projectDTO.getMoneyNeeded())
                .categories(categoryNameMapper.convertListToModel(projectDTO.getCategories()))
                .build();
    }

}
