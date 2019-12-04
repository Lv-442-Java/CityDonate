package com.softserve.ita.java442.cityDonut.controller.login;

import com.softserve.ita.java442.cityDonut.security.CookieProvider;
import com.softserve.ita.java442.cityDonut.security.JWTTokenProvider;
import com.softserve.ita.java442.cityDonut.security.facebook.FacebookServiceImpl;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/api/v1")
public class FacebookController {

    private FacebookServiceImpl facebookService;
    private UserServiceImpl userService;
    private CookieProvider cookieProvider;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public FacebookController(FacebookServiceImpl facebookService, UserServiceImpl userService,
                              CookieProvider cookieProvider, JWTTokenProvider jwtTokenProvider) {
        this.facebookService = facebookService;
        this.userService = userService;
        this.cookieProvider = cookieProvider;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping(value = "/facebooklogin")
    public RedirectView googleLogin() {
        RedirectView redirectView = new RedirectView();
        String url = facebookService.getLogin();
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping(value = "/facebook")
    public String google(@RequestParam("code") String code) {
        String accessToken = facebookService.getAccessToken(code);
        return "redirect:api/v1/facebookProfileData/" + accessToken;
    }

    @GetMapping(value = "/facebookProfileData/{accessToken:.+}")
    public String googleProfileData(@PathVariable String accessToken, HttpServletResponse response) {
        System.out.println(accessToken);
//        try {
//            User userSocial = facebookService.getUserProfile(accessToken);
//            if(userSocial.getEmail() == null){
//               return "Please add email to your facebook account";
//            }
//
//            com.softserve.ita.java442.cityDonut.model.User userDataBase = userService.findUserByEmail(userSocial.getEmail());
//
//            if (userDataBase == null){
//                throw new UserPrincipalNotFoundException("User with email " + userSocial.getEmail().toUpperCase() + "not found!");
//            }else
//                response.addCookie(cookieProvider.createCookie(jwtTokenProvider.generateAccessToken(userDataBase)));
//        } catch (UserPrincipalNotFoundException e) {
//            throw new BadCredentialsException("Invalid email or password");
//        }
        return null;
    }
}
