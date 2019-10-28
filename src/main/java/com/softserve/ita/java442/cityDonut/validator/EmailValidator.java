package com.softserve.ita.java442.cityDonut.validator;

import com.softserve.ita.java442.cityDonut.exception.InvalidEmailException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public boolean validateEmail(String email) throws InvalidEmailException {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
