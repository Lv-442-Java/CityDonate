package com.softserve.ita.java442.cityDonut.exception;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Map;

public class InvalidUserRegistrationDataException extends RuntimeException {

    public InvalidUserRegistrationDataException(String message) {
        super(message);
    }
    public InvalidUserRegistrationDataException(Map<String, String> messages) {
        super(new JSONObject(messages).toString());
    }
}
