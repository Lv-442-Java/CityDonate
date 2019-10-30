package com.softserve.ita.java442.cityDonut.service;

import com.softserve.ita.java442.cityDonut.model.User;

public interface UserService {
    User findUserByEmail(String email);
    User getCurrentUser();
}
