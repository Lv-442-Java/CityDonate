package com.softserve.ita.java442.cityDonut.constant;

public class ErrorMessage {
    public static final String USER_NOT_FOUND_BY_ID = "The user does not exist by this id: ";
    public static final String PROJECT_NOT_FOUND_BY_ID = "The project does not exist by this id";
    public static final String INCORRECT_USER_PASSWORD = "The user`s password is incorrect: ";

    public static final String FILE_NOT_FOUND= "File not found: ";
    public static final String COULD_NOT_CREATE_FILE_DIRECTORY = "Could not create the directory where the uploaded files will be stored.";
    public static final String INVALID_CHARACTER = "Sorry! Filename contains invalid path sequence ";
    public static final String COULD_NOT_STORE_FILE = " could not be stored file. Please try again!";
    public static final String NOT_DETERMINED_FILE_TYPE = "Could not determine file type.";
  
    public static final String INCORRECT_EMAIL = "Such email exists: ";
    public static final String INVALID_EMAIL = "Email is wrong: ";

    public static final String USER_HAS_NOT_PROJECT_WITH_ID = "The user hasn`t project with id: ";
    public static final String CATEGORY_NOT_FOUND_BY_NAME = "The category does not exist by name: ";
    public static final String NULL_NAME_ERROR = "name field cannot be empty";
    public static final String PROJECT_NAME_SIZE_ERROR = "the project name must be between 4 and 50 symbols";
    public static final String PROJECT_DESCRIPTION_SIZE_ERROR = "the project description must be less than 1000 symbols";
    public static final String PROJECT_LOCATION_SIZE_ERROR = "the project location must be less than 255 symbols";
    public static final String PROJECT_LOCATION_PARAM_RANGE_ERROR = "location param must be a decimal number in range (-180; 180)";
    public static final String PROJECT_LOCATION_PARAM_FORMAT_ERROR = "location param can contain only 6 digits after decimal point";

}
