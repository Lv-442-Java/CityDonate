package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getById(long id);

    Optional<Project> findByOwnerAndId(User owner, long id);

    List<Project> findProjectsByProjectStatus(ProjectStatus projectStatus);

    List<Project> getAllByOwner(User user);

    List<Project> findAllByModeratorsIn(List<User> moderators);

    @Query("select MAX(p.moneyNeeded) from Project p")
    Long getMaxByMoneyNeeded();
}
