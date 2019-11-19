package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import org.springframework.stereotype.Component;

@Component
public class ProjectByUserDonateMapper implements GeneralMapper<DonatedUserProject, ProjectByUserDonateDto> {

    @Override
    public ProjectByUserDonateDto convertToDto(DonatedUserProject donatedUserProject) {
        return ProjectByUserDonateDto.builder()
                .id(donatedUserProject.getProject().getId())
                .name(donatedUserProject.getProject().getName())
                .projectStatus(donatedUserProject.getProject().getProjectStatus())
                .donateCount(donatedUserProject.getDonateCount())
                .donateSum(donatedUserProject.getSum())
                .build();
    }

    @Override
    public DonatedUserProject convertToModel(ProjectByUserDonateDto dto) {
        return null;
    }
}
