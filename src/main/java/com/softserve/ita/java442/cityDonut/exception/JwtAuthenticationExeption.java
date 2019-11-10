package com.softserve.ita.java442.cityDonut.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationExeption extends AuthenticationException {
    public JwtAuthenticationExeption(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationExeption(String msg) {
        super(msg);
    }
}
