package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.user.UserRegistrationDto;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserServiceImpl userServiceimpl;

    @PostMapping("/")
    public ResponseEntity<UserRegistrationDto> registration(@RequestBody UserRegistrationDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceimpl.registerUser(dto));
    }

    @GetMapping("/activate")
    public ResponseEntity<Void> activate(@RequestParam(name = "activationCode")String activationCode){
        userServiceimpl.activateUserByCode(activationCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
