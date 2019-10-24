package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditPasswordDto;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public UserEditDto getUser(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PutMapping()
    public ResponseEntity<UserEditDto> updateUser(
            @RequestBody UserEditDto userEditDto) {
        userService.update(userEditDto);
        return new ResponseEntity<>(userEditDto, HttpStatus.OK);
    }

    @PutMapping("/change_password")
    public ResponseEntity<UserEditPasswordDto> updatePassword(
            @RequestBody UserEditPasswordDto userEditPasswordDto) {
        userService.changePassword(userEditPasswordDto);
        return new ResponseEntity<>(userEditPasswordDto, HttpStatus.OK);
    }
}
