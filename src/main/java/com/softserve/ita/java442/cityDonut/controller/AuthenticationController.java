package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.authentication.AuthenticationRequestDto;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

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
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try{
            String userEmail = requestDto.getUserEmail();
            System.out.println(requestDto.getUserEmail());
            System.out.println(jwtTokenProvider.passwordEncoder().encode(requestDto.getUserEmail()));
            System.out.println("Chek "+authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail,
                    jwtTokenProvider.passwordEncoder().encode(requestDto.getPassword()))).isAuthenticated());
            User user = userService.findUserByEmail(userEmail);
            System.out.println("User controller "+user);

            if(user == null)
                throw  new UserPrincipalNotFoundException("User with email "+userEmail.toUpperCase() + "not found!");

            String token = jwtTokenProvider.generateToken(user);
            Map<Object,Object> response = new HashMap<>();
            response.put("token",token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException | UserPrincipalNotFoundException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
