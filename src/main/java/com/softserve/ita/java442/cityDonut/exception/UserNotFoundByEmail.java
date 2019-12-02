package com.softserve.ita.java442.cityDonut.exception;

public class UserNotFoundByEmail extends RuntimeException  {
    public UserNotFoundByEmail(String message) {
        super(message);
    }
}
