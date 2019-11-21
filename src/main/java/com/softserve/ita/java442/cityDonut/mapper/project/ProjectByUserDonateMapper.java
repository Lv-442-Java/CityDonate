package com.softserve.ita.java442.cityDonut.mapper.project;

import com.softserve.ita.java442.cityDonut.dto.project.ProjectByUserDonateDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.mapper.category.CategoryMapper;
import com.softserve.ita.java442.cityDonut.mapper.projectStatus.ProjectStatusMapper;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectByUserDonateMapper implements GeneralMapper<DonatedUserProject, ProjectByUserDonateDto> {

    @Autowired
    private ProjectStatusMapper projectStatusMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ProjectByUserDonateDto convertToDto(DonatedUserProject donatedUserProject) {
        return ProjectByUserDonateDto.builder()
                .id(donatedUserProject.getProject().getId())
                .name(donatedUserProject.getProject().getName())
                .projectStatusDto(projectStatusMapper.convertToDto(donatedUserProject.getProject().getProjectStatus()))
                .categories(categoryMapper.convertListToDto(donatedUserProject.getProject().getCategories()))
                .moneyNeeded(donatedUserProject.getProject().getMoneyNeeded())
                .galleryId(donatedUserProject.getProject().getGallery().getId())
                .donateCount(donatedUserProject.getDonateCount())
                .donateSum(donatedUserProject.getSum())
                .build();
    }

    @Override
    public DonatedUserProject convertToModel(ProjectByUserDonateDto dto) {
        return null;
    }
}
