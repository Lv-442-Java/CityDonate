package com.softserve.ita.java442.cityDonut.dto.fieldsCheck;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldsCheckDto {

    private long id;

    private boolean isNameValid;

    private boolean isDescriptionValid;

    private boolean isLocationValid;

    private boolean isMoneyNeededValid;

    private boolean arePhotosValid;

    private boolean areDocumentsValid;
}

