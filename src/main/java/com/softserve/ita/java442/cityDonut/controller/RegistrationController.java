package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public HttpStatus registration(@RequestBody UserRegistrationDto dto) {
        userService.validateUser(dto);
        userService.registerUser(dto);
        return HttpStatus.OK;
    }

    @GetMapping("/activationUser")
    public ResponseEntity<Void> activationUser(@RequestParam(name = "activationCode") String activationCode) {
        userService.activateUserByCode(activationCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}