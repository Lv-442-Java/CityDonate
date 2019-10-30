package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import com.softserve.ita.java442.cityDonut.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectByUserDonateMapper implements GeneralMapper<Project, ProjectByUserDonateDto> {

    public ProjectByUserDonateDto convertToDto(Project project, DonatedUserProject donatedUserProject) {
        return ProjectByUserDonateDto.builder()
                .id(project.getId())
                .name(project.getName())
                .media(project.getMedia())
                .projectStatus(project.getProjectStatus())
                .donateCount(donatedUserProject.getDonateCount())
                .donateSum(donatedUserProject.getSum())
                .build();
    }

    @Override
    public ProjectByUserDonateDto convertToDto(Project model) {
        return null;
    }

    @Override
    public Project convertToModel(ProjectByUserDonateDto dto) {
        return null;
    }
}
