package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Category;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.ProjectStatus;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getById(long id);

    Optional<Project> findByOwnerAndId(User owner, long id);

    @Query("select p from Project p where " +
            "(select count (distinct c) FROM p.categories c where c in (:categories) ) >= " +
            "(select count (distinct c) FROM Category c where c in (:categories))" +
            "AND p.projectStatus = :projectStatus AND p.moneyNeeded between :moneyFrom and :moneyTo")
    List<Project> getFilteredProjects(@Param("categories") List<Category> categories,
                                          @Param("projectStatus") ProjectStatus projectStatus,
                                          @Param("moneyFrom") long moneyFrom,
                                          @Param("moneyTo") long moneyTo,
                                          Pageable pageable);
}
