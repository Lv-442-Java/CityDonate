package com.softserve.ita.java442.cityDonut.exception;

public class NotEnoughPermission extends RuntimeException {
    public NotEnoughPermission(String message) {
        super(message);
    }
}