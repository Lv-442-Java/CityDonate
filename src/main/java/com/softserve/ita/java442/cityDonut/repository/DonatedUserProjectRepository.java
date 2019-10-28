package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DonatedUserProjectRepository extends JpaRepository<Donate, Long> {
    @Query(value = "SELECT new com.softserve.ita.java442.cityDonut.model.DonatedUserProject(" +
            "v.id, count(v.sum), sum(v.sum), v.project.id, v.user.id) " +
            "FROM Donate v " +
            "WHERE v.user.id = :user_id " +
            "GROUP BY v.project.id, v.user.id")
    List<DonatedUserProject> findDonatedUserProject(@Param("user_id") long userId);
}
