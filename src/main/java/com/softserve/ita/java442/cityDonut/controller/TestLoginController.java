package com.softserve.ita.java442.cityDonut.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLoginController {

    @GetMapping("/")
    public String getHome(){
        return ("<h1>Welcome  dear guest!</h1>");
    }

    @GetMapping("/user")
    public String getUser(){
        return ("<h1>Hello User</h1>");
    }
    @GetMapping("/admin")
    public String getAdmin(){
        return ("<h1>Hello Admin</h1>");
    }
}
