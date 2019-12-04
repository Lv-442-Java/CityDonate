package com.softserve.ita.java442.cityDonut.controller.login;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.exception.JwtAuthenticationExeption;
import com.softserve.ita.java442.cityDonut.exception.UserNotFoundByEmail;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.security.CookieProvider;
import com.softserve.ita.java442.cityDonut.security.Helper;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.security.google.GoogleServiceImpl;
import com.softserve.ita.java442.cityDonut.security.google.UserInfo;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
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
        return "redirect:/api/v1/googleProfileData/" + accessToken;
    }

    @GetMapping(value = "/googleProfileData/{accessToken:.+}")
    public void googleProfileData(@PathVariable String accessToken, HttpServletResponse response) throws IOException {
        try {
            UserInfo userSocial = googleService.getUserProfile(accessToken);
            if (userService.existsUserByEmail(userSocial.getEmail())) {
                User userDataBase = userService.findUserByEmail(userSocial.getEmail());

                List<Cookie> list = Helper.createList(
                        cookieProvider.createCookie("JWT", jwtTokenProvider.generateAccessToken(userDataBase)),
                        cookieProvider.createCookie("jwt", jwtTokenProvider.generateRefreshToken())
                );
                for (Cookie cookie : list) {
                    response.addCookie(cookie);
                }
                response.sendRedirect("http://localhost:3000");
            }
        } catch (UserNotFoundByEmail e) {
            response.sendError(404, ErrorMessage.USER_DOESNT_REGISTERED);
        } catch (JwtAuthenticationExeption e) {
            response.sendError(400, ErrorMessage.CANNOT_ENTRY_WITH_GOOGLE_SERVICES);
        }

    }
}



