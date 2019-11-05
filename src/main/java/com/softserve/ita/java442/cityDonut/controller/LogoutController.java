package com.softserve.ita.java442.cityDonut.controller;

import com.softserve.ita.java442.cityDonut.security.CookieProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LogoutController {

    @Autowired
    private CookieProvider cookieProvider;

    @GetMapping("/sign-out")
    public void getHome(HttpServletRequest request, HttpServletResponse response) {
        response.addCookie(cookieProvider.deleteCookie());
    }
}
