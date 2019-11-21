package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.FieldsCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldsCheckRepository extends JpaRepository<FieldsCheck, Long> {
    List<FieldsCheck> getAllByProjectId(long project_id);
}
