package com.softserve.ita.java442.cityDonut.security.congiquration;

import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findUserByFirstName(String email);
}
