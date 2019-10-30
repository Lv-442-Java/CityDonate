package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.authentication.AuthenticationRequestDto;
import com.softserve.ita.java442.cityDonut.exeption.IncorrectPasswordException;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserServiceImpl userService;

    public AuthenticationController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider,
                                    UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping(value = "/auth")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
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

            String token = jwtTokenProvider.generateToken(user);
            Map<Object, Object> response = new HashMap<>();
            response.put("userName", user.getEmail());
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException | UserPrincipalNotFoundException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @GetMapping("/")
    public String getHome() {
        return ("<h1>Welcome  dear guest!</h1>");
    }

    @GetMapping("/auth/user")
    public String getUser() {
        return ("<h1>Hello User</h1>" + userService.getCurrentUser().getEmail());
    }

    @GetMapping("/auth/admin")
    public String getAdmin() {
        return ("<h1>Hello Admin</h1>");
    }
}
