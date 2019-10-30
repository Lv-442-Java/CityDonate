package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.dto.project.MainProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.project.ProjectInfoDto;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    public User getUserById(long id);

    User findByEmail(String email);

    List<ProjectInfoDto> getFreeProject();

    MainProjectInfoDto addModeratorToProject(long project_id, long moderator_id);

    void changeProjectStatus(long project_id, ProjectStatus projectStatus);
}
