package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.dto.project.ProjectInfoDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserEditPasswordDto;
import com.softserve.ita.java442.cityDonut.dto.user.UserRoleDto;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public UserEditDto getUser() {
        return userService.getUserEditDto();
    }

    @PutMapping()
    public ResponseEntity<UserEditDto> updateUser(
            @Valid @RequestBody UserEditDto userEditDto) {
        userService.update(userEditDto);
        return new ResponseEntity<>(userEditDto, HttpStatus.OK);
    }

    @PutMapping("/change_password")
    public ResponseEntity<UserEditPasswordDto> updatePassword(
            @RequestBody UserEditPasswordDto userEditPasswordDto) {
        userService.changePassword(userEditPasswordDto);
        return new ResponseEntity<>(userEditPasswordDto, HttpStatus.OK);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectInfoDto>> getMyProject(){
        return new ResponseEntity<>(userService.getProjects(),HttpStatus.OK);
    }

    @GetMapping("/{userId}/role")
    public ResponseEntity<UserRoleDto> getUserWithRole(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getUserRoleDto(userId), HttpStatus.OK);
    }
}
