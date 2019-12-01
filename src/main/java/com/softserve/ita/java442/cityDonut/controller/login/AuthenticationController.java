package com.softserve.ita.java442.cityDonut.controller.login;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.dto.authentication.AuthenticationRequestDto;
import com.softserve.ita.java442.cityDonut.exception.IncorrectPasswordException;
import com.softserve.ita.java442.cityDonut.exception.UserNotFoundByEmail;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.security.CookieProvider;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private UserServiceImpl userService;
    private CookieProvider cookieProvider;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider,
                                    UserServiceImpl userService, CookieProvider cookieProvider, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.cookieProvider = cookieProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @RequestMapping(value = "/api/v1/sign-in")
    public void login(@RequestBody AuthenticationRequestDto requestDto, HttpServletResponse response) throws IOException {
        String userEmail = requestDto.getUserEmail();
        try {
            if (userService.existsUserByEmail(userEmail)) {

                if (userService.comparePasswordLogin(requestDto, passwordEncoder)) {
                    authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(userEmail, requestDto.getPassword()));
                }
                User user = userService.findUserByEmail(userEmail);

                List<Cookie> cookieList = createList(
                        cookieProvider.createCookie("JWT", jwtTokenProvider.generateAccessToken(user)),
                        cookieProvider.createCookie("jwt", jwtTokenProvider.generateRefreshToken())
                );

                for (Cookie k : cookieList)
                    response.addCookie(k);

            }
        } catch (UserNotFoundByEmail ex) {
            response.sendError(404, ErrorMessage.USER_NOT_FOUND_WITH_THIS_EMAIL + userEmail);
        } catch (IncorrectPasswordException ex) {
            response.sendError(400, ErrorMessage.INVALID_EMAIL_OR_PASSWORD);
        }
    }

    private List<Cookie> createList(Cookie... cookies) {
        List<Cookie> list = new ArrayList<>();
        for (Cookie k : cookies) {
            list.add(k);
        }
        return list;
    }
    @GetMapping("/home")
    public String getHome() {
        return ("Welcome  dear guest!");
    }
}
/*
secret.key.for.token=CityDonut
expired.time.access.token=120000
expired.time.refresh.token=3600000
expired.time.for.cookie=-1
server.port=8091
 */