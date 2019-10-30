package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryBoardRepository extends JpaRepository<StoryBoard, Long> {
    StoryBoard getStoryBoardByProject_Id(long projectId);
}
