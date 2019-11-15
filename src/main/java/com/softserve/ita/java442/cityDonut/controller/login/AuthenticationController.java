package com.softserve.ita.java442.cityDonut.controller.login;

import com.softserve.ita.java442.cityDonut.dto.authentication.AuthenticationRequestDto;
import com.softserve.ita.java442.cityDonut.exception.IncorrectPasswordException;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.security.CookieProvider;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
public class AuthenticationController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserServiceImpl userService;
    private CookieProvider cookieProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider,
                                    UserServiceImpl userService, CookieProvider cookieProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.cookieProvider = cookieProvider;
    }

    @PostMapping
    @RequestMapping(value = "/sign-in")
    public void login(@RequestBody AuthenticationRequestDto requestDto, HttpServletResponse response) {
        try {
            String userEmail = requestDto.getUserEmail();
            User user = userService.findUserByEmail(userEmail);

            if (user == null) {
                throw new UserPrincipalNotFoundException("User with email " + userEmail.toUpperCase() + "not found!");
            } else if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail,
                        requestDto.getPassword()));
            } else
                throw new IncorrectPasswordException("Incorrect user password!");

            response.addCookie(cookieProvider.createCookie(jwtTokenProvider.generateToken(user)));

        } catch (AuthenticationException | UserPrincipalNotFoundException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }


    @GetMapping("/")
    public String getHome() {
        return ("Welcome  dear guest!");
    }

    @GetMapping("/user")
    public String getUser() {
        return ("Hello User");
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return ("Hello Admin");
    }
}
