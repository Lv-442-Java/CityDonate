package com.softserve.ita.java442.cityDonut.controller.login;

import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.security.CookieProvider;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.security.google.GoogleServiceImpl;
import com.softserve.ita.java442.cityDonut.security.google.UserInfo;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@Controller
public class GoogleController {

    private JWTTokenProvider jwtTokenProvider;
    private UserServiceImpl userService;
    private CookieProvider cookieProvider;
    private GoogleServiceImpl googleService;

    @Autowired
    public GoogleController(JWTTokenProvider jwtTokenProvider, UserServiceImpl userService,
                            CookieProvider cookieProvider, GoogleServiceImpl googleService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.cookieProvider = cookieProvider;
        this.googleService = googleService;
    }

    @GetMapping(value = "/googlelogin")
    public RedirectView googleLogin() {
        RedirectView redirectView = new RedirectView();
        String url = googleService.getLogin();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/google")
    public String google(@RequestParam("code") String code) {
        String accessToken = googleService.getAccessToken(code);
        return "redirect:/googleProfileData/" + accessToken;
    }

    @GetMapping(value = "/googleProfileData/{accessToken:.+}")
    public void googleProfileData(@PathVariable String accessToken, HttpServletResponse response) {
        try {

            UserInfo userSocial = googleService.getUserProfile(accessToken);
            User userDataBase = userService.findUserByEmail(userSocial.getEmail());
            System.out.println(userSocial.getEmail());

            if (userDataBase == null){
                throw new UserPrincipalNotFoundException("User with email " + userSocial.getEmail().toUpperCase() + "not found!");
            }else
                response.addCookie(cookieProvider.createCookie(jwtTokenProvider.generateAccessToken(userDataBase)));

        } catch (UserPrincipalNotFoundException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }
}
