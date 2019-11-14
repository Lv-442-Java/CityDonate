package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserServiceImpl userServiceimpl;

    @PostMapping("/")
    public HttpStatus registration(@RequestBody UserRegistrationDto dto) {
        userServiceimpl.registerUser(dto);
        return HttpStatus.OK;
    }

    @GetMapping("/activationUser")
    public ResponseEntity<Void> activationUser(@RequestParam(name = "activationCode") String activationCode) {
        userServiceimpl.activateUserByCode(activationCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}