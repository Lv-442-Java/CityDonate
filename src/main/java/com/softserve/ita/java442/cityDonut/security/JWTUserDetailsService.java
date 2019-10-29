package com.softserve.ita.java442.cityDonut.security;

import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(userEmail);
        System.out.println("User "+user);
        if(user == null)
            throw new  UsernameNotFoundException("User with this email "+userEmail+" not found!");
        return new UserPrincipal(user);
    }
}
