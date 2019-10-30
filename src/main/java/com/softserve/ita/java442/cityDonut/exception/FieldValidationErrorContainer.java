package com.softserve.ita.java442.cityDonut.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FieldValidationErrorContainer implements Serializable {

    private String name;
    private String message;

    public FieldValidationErrorContainer(FieldError fieldError) {
        name = fieldError.getField();
        message = fieldError.getDefaultMessage();
    }

}
