package com.softserve.ita.java442.cityDonut.mapper.fieldsCheck;

import com.softserve.ita.java442.cityDonut.dto.fieldsCheck.FieldsCheckDto;
import com.softserve.ita.java442.cityDonut.mapper.GeneralMapper;
import com.softserve.ita.java442.cityDonut.model.FieldsCheck;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FieldsCheckMapper implements GeneralMapper<FieldsCheck, FieldsCheckDto> {

    @Override
    public FieldsCheckDto convertToDto(FieldsCheck model) {
        return FieldsCheckDto.builder()
                .id(model.getId())
                .areDocumentsValid(model.isAreDocumentsValid())
                .arePhotosValid(model.isArePhotosValid())
                .isDescriptionValid(model.isDescriptionValid())
                .isLocationValid(model.isLocationValid())
                .isMoneyNeededValid(model.isMoneyNeededValid())
                .isNameValid(model.isNameValid())
                .build();
    }

    @Override
    public FieldsCheck convertToModel(FieldsCheckDto dto) {
        return FieldsCheck.builder()
                .id(dto.getId())
                .areDocumentsValid(dto.isAreDocumentsValid())
                .arePhotosValid(dto.isArePhotosValid())
                .isDescriptionValid(dto.isDescriptionValid())
                .isLocationValid(dto.isLocationValid())
                .isMoneyNeededValid(dto.isMoneyNeededValid())
                .isNameValid(dto.isNameValid())
                .build();
    }

    public List<FieldsCheckDto> convertListToDto(List<FieldsCheck> models) {
        List<FieldsCheckDto> dtos = new ArrayList<>();
        for (FieldsCheck model : models) {
            dtos.add(convertToDto(model));
        }
        return dtos;
    }
}
