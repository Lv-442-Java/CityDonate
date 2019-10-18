package com.softserve.ita.java442.cityDonut.repository;


import com.softserve.ita.java442.cityDonut.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByRole (String role);
    Optional<Role> findById(Integer id);

}

