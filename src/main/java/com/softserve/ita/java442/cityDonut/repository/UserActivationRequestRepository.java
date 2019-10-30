package com.softserve.ita.java442.cityDonut.repository;

import com.softserve.ita.java442.cityDonut.model.UserActivationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivationRequestRepository extends JpaRepository<UserActivationRequest,Long> {
    UserActivationRequest findByActivationCode(String activationCode);
}
