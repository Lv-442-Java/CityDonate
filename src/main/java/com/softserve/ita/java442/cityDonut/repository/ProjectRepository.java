package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> getProjectsByProjectStatusAndMoneyNeededBetween(ProjectStatus status, long moneyFrom, long moneyTo);
}
