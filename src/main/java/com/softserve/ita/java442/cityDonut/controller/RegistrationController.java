package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.service.UserService;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    UserServiceImpl userServiceimpl;

    @PostMapping("/")
    public ResponseEntity<UserRegistrationDto> registration(@RequestBody UserRegistrationDto dto) {
        System.out.println(dto);
        return ResponseEntity.status(HttpStatus.OK).body(userServiceimpl.saveUser(dto));
    }
}
