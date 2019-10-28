package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.Donate;
import com.softserve.ita.java442.cityDonut.model.DonatedUserProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonateRepository extends JpaRepository<Donate, Long> {
    List<Donate> getByProjectId(long id);
//    @Query(value = "SELECT new com.softserve.ita.java442.cityDonut.model.DonatedUserProject(v.id, count(v.sum), sum(v.sum), v.project.id, v.user.id) FROM Donate v group by v.project.id, v.user.id",
//            nativeQuery = false)
//    List<DonatedUserProject> findDonatedUserProject();
}
