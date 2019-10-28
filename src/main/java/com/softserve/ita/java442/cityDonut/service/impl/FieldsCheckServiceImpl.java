package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;
import com.softserve.ita.java442.cityDonut.mapper.fieldsCheck.FieldsCheckMapper;
import com.softserve.ita.java442.cityDonut.model.FieldsCheck;
import com.softserve.ita.java442.cityDonut.repository.FieldsCheckRepository;
import com.softserve.ita.java442.cityDonut.service.FieldsCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldsCheckServiceImpl implements FieldsCheckService {

    @Autowired
    private FieldsCheckRepository fieldsCheckRepository;

    @Autowired
    private FieldsCheckMapper fieldsCheckMapper;

    @Override
    public List<FieldsCheckDto> getAllByProject_Id(long project_id) {
        return fieldsCheckMapper.convertListToDto(fieldsCheckRepository.getAllByProject_Id(project_id));
    }
}
