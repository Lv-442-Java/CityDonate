package com.softserve.ita.java442.cityDonut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @RequestMapping(value = "/url",method = RequestMethod.GET)
    String getHome(){
        return "index.jsp";
    }
}
