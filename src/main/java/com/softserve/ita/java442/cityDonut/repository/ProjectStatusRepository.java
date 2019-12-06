package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
    ProjectStatus getProjectStatusByStatus(String status);

    List<ProjectStatus> getByStatusIsNotIn(List<String> statusesBeforeCostsGathering);

    List<ProjectStatus> findAll();
}
