package com.softserve.ita.java442.cityDonut.security;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.exception.JwtAuthenticationExeption;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTTokenFilter extends GenericFilterBean {
    private JWTTokenProvider jwtTokenProvider;
    private CookieProvider cookieProvider;
    private UserService userService;

    public JWTTokenFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public JWTTokenFilter(JWTTokenProvider jwtTokenProvider, CookieProvider cookieProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieProvider = cookieProvider;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String refreshToken = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest, "jwt");
        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest, "JWT");
        try{
            if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                if(refreshToken != null && jwtTokenProvider.validateToken(refreshToken)){
                    User user = userService.findUserByEmail(jwtTokenProvider.getUserEmail(accessToken));
                    ((HttpServletResponse) servletResponse).addCookie(cookieProvider.createCookie("JWT",jwtTokenProvider.generateAccessToken(user)));
                }else{
                    throw  new JwtAuthenticationExeption(ErrorMessage.ALL_TOKENS_ARE_EXPIRED);
                }
            }
        }catch (JwtAuthenticationExeption e){

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
