package com.softserve.ita.java442.cityDonut.security.congiquration;


import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        System.out.println("User : "+user);
        return new CustomUserDetails(user);
    }
}
