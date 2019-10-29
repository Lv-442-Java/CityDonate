package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;

import java.util.List;

public interface FieldsCheckService {
    List<FieldsCheckDto> getAllByProjectId(long project_id);

    FieldsCheckDto update(FieldsCheckDto fieldsCheckDto);
}
