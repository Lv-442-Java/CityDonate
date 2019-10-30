package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getById(long id);

    Optional<Project> findByOwnerAndId(User owner, long id);

    List<Project> getProjectsByProjectStatusAndMoneyNeededBetween(ProjectStatus status, long moneyFrom, long moneyTo);
}
