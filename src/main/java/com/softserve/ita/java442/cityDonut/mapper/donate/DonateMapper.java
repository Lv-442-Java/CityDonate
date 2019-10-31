package com.softserve.ita.java442.cityDonut.mapper.donate;

import com.softserve.ita.java442.cityDonut.dto.donateDto.DonateDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.repository.ProjectRepository;
import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DonateMapper implements GeneralMapper<Donate, DonateDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public DonateDto convertToDto(Donate model) {
        return DonateDto.builder()
                .id(model.getId())
                .date(model.getDate())
                .sum(model.getSum())
                .userId(model.getUser().getId())
                .projectId(model.getProject().getId())
                .build();
    }

    @Override
    public Donate convertToModel(DonateDto dto) {
        return Donate.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .sum(dto.getSum())
                .user(userRepository.getUserById(dto.getUserId()))
                .project(projectRepository.getById(dto.getProjectId()))
                .build();
    }
}
