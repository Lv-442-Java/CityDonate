package com.softserve.ita.java442.cityDonut.constant;

public class ErrorMessage {
    public static final String USER_NOT_FOUND_BY_ID = "The user does not exist by this id: ";
    public static final String STORYBOARD_NOT_FOUND_BY_ID = "The storyBoard does not exist by this id: ";
    public static final String PROJECT_NOT_FOUND_BY_ID = "The project does not exist by this id";
    public static final String INCORRECT_USER_PASSWORD = "The user`s password is incorrect: ";
    public static final String FILE_NOT_FOUND = "File not found: ";
    public static final String FILE_NOT_FOUND_BY_NAME_AND_PROJECT_ID = "File not found in DB: ";
    public static final String FIELD_INFO_NOT_FOUND_BY_ID = "No field info exists by this id";
    public static final String FIELD_INFO_NOT_FOUND_BY_PROJECT_ID = "No field info exists by this project id";
    public static final String INVALID_CHARACTER = "Sorry! Filename contains invalid path sequence ";
    public static final String COULD_NOT_STORE_FILE = " could not be stored file. Please try again!";
    public static final String NOT_DETERMINED_FILE_TYPE = "Could not determine file type.";
    public static final String INVALID_USER_REGISTRATION_DATA = "Користувач з таким email вже зареєстрований";
    public static final String INVALID_EMAIL = "Не вірно введений email";
    public static final String USER_HAS_NOT_PROJECT_WITH_ID = "The user hasn`t project with id: ";
    public static final String CATEGORY_NOT_FOUND_BY_NAME = "The category does not exist by name: ";
    public static final String NULL_NAME_ERROR = "name field cannot be empty";
    public static final String PROJECT_NAME_SIZE_ERROR = "the project name must be between 4 and 50 symbols";
    public static final String PROJECT_DESCRIPTION_SIZE_ERROR = "the project description must be less than 1000 symbols";
    public static final String PROJECT_LOCATION_SIZE_ERROR = "the project location must be less than 255 symbols";
    public static final String PROJECT_LOCATION_PARAM_RANGE_ERROR = "location param must be a decimal number in range (-180; 180)";
    public static final String PROJECT_LOCATION_PARAM_FORMAT_ERROR = "location param can contain only 6 digits after decimal point";
    public static final String PROJECT_BUDGET_ERROR = "project budget must be greater than 0 and less than 10000000000";
    public static final String FIELD_CANT_BE_NULL = "The field can`t be null";
    public static final String INCORRECT_SIZE = "Length should be between 4 and 50 symbols";
    public static final String PROJECT_STATUS_NOT_FOUND = "The project status not found: ";
    public static final String NOT_ENOUGH_PERMISSION = "The user have not enough permission ";
    public static final String ROLE_NOT_FOUND = "Role not found:  ";
    public static final String USER_HAS_NOT_ACCESS_TO_COMMENT = "You can not modify comment with id: ";
    public static final String INVALID_USER_PASSWORD = "Пароль недостатньо захищений. Пароль повинен містити від 4 до 15 цифр або літер";
    public static final String USER_NOT_FOUND_WITH_THIS_EMAIL = "The user does not exists by this EMAIL: ";
    public static final String INVALID_EMAIL_OR_PASSWORD = "INVALID email or password ";
    public static final String JWT_TOKEN_IS_EXPIRED = "JWT token is expired";
    public static final String DOESNT_AUTHENTICATED = "User doesn't authenticated by thia email: ";
    public static final String ALL_TOKENS_ARE_EXPIRED = "All tokens are expired.";

}
