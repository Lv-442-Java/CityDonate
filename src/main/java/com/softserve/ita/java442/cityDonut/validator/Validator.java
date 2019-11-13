package com.softserve.ita.java442.cityDonut.validator;

import com.softserve.ita.java442.cityDonut.exception.InvalidEmailException;
import com.softserve.ita.java442.cityDonut.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validator {

    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]{4,15}$";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public boolean validateEmail(String email) throws InvalidEmailException {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) throws InvalidPasswordException {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
