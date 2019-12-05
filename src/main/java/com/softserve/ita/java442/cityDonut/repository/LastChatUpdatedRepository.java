package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.LastChatUpdated;
import com.softserve.ita.java442.cityDonut.model.Project;
import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LastChatUpdatedRepository extends JpaRepository<LastChatUpdated, Long> {

    Optional<LastChatUpdated> findByUserAndProject(User user, Project project);

    List<LastChatUpdated> findAllByProject(Project project);

}
