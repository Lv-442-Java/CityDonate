package com.softserve.ita.java442.cityDonut.security.congiquration;

import com.softserve.ita.java442.cityDonut.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserRepo u;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = u.findUserByFirstName(s);
        if(user == null)
            throw new UsernameNotFoundException("User not found");
        return new CustomUserDetails(user);
    }
}
