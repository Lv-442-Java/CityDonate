package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.StoryBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryBoardRepository extends JpaRepository<StoryBoard, Long> {
    List<StoryBoard> getStoryBoardsByProject_Id(long projectId);

    List<StoryBoard> getStoryBoardsByProject_IdAndIsVerifiedIsTrueOrderByDateDesc(long projectId);
}
