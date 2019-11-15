package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Donate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateRepository extends JpaRepository<Donate, Long> {
    List<Donate> getByProjectId(long id, Pageable pageable);
    List<Donate> getByProjectIdAndUserId(long id, long userId, Pageable pageable);

    @Query("SELECT sum(d.sum) from Donate d where d.project.id = :projectId")
    Long getSumByProjectId(@Param("projectId") long id);

    @Query("SELECT count(distinct d.user.id) FROM Donate d where d.project.id = :projectId")
    Long getCountOfBenefactorsByProjectId(@Param("projectId") long id);
}
