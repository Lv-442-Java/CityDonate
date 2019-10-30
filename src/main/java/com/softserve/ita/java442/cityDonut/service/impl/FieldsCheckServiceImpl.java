package com.softserve.ita.java442.cityDonut.service.impl;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;
import com.softserve.ita.java442.cityDonut.exception.NotFoundException;
import com.softserve.ita.java442.cityDonut.mapper.fieldsCheck.FieldsCheckMapper;
import com.softserve.ita.java442.cityDonut.model.FieldsCheck;
import com.softserve.ita.java442.cityDonut.repository.FieldsCheckRepository;
import com.softserve.ita.java442.cityDonut.service.FieldsCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FieldsCheckServiceImpl implements FieldsCheckService {

    @Autowired
    private FieldsCheckRepository fieldsCheckRepository;

    @Autowired
    private FieldsCheckMapper fieldsCheckMapper;

    @Override
    public List<FieldsCheckDto> getAllByProjectId(long project_id) {
        return fieldsCheckMapper.convertListToDto(fieldsCheckRepository.getAllByProjectId(project_id));
    }

    @Override
    @Transactional
    public FieldsCheckDto update(FieldsCheckDto fieldsCheckDto) {
        FieldsCheck fieldsCheck;
        fieldsCheck = fieldsCheckRepository
                .findById(fieldsCheckDto.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.FIELD_INFO_NOT_FOUND_BY_ID));
        fieldsCheck.setAreDocumentsValid(fieldsCheckDto.isAreDocumentsValid());
        fieldsCheck.setArePhotosValid(fieldsCheckDto.isArePhotosValid());
        fieldsCheck.setDescriptionValid(fieldsCheckDto.isDescriptionValid());
        fieldsCheck.setLocationValid(fieldsCheckDto.isLocationValid());
        fieldsCheck.setMoneyNeededValid(fieldsCheckDto.isMoneyNeededValid());
        fieldsCheck.setNameValid(fieldsCheckDto.isNameValid());
        fieldsCheckRepository.save(fieldsCheck);
        return fieldsCheckDto;
    }
}
