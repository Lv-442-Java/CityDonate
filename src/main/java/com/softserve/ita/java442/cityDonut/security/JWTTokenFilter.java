package com.softserve.ita.java442.cityDonut.security;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.exception.JwtAuthenticationExeption;
import com.softserve.ita.java442.cityDonut.service.UserService;
import com.softserve.ita.java442.cityDonut.service.impl.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class JWTTokenFilter extends GenericFilterBean {
    @Value("${expired.time.for.cookie}")
    private int EXPIRED_TIME_FOR_COOKIE;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public JWTTokenFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String refreshToken = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest, "jwt");
        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest, "JWT");
        if (refreshToken != null && accessToken != null) {
            try {
                if (jwtTokenProvider.validateToken(accessToken)) {
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    if (!jwtTokenProvider.validateToken(refreshToken)) {
                       JwtToken jwtToken = jwtTokenProvider.UserData(accessToken);

                        Cookie cookie = new Cookie("JWT",jwtTokenProvider
                                .generateAccessToken(jwtToken.getId(),jwtToken.getRole(),jwtToken.getEmail()));
                        cookie.setMaxAge(EXPIRED_TIME_FOR_COOKIE);
                        cookie.setDomain("localhost");
                        cookie.setPath("/");
                    } else {
                        ((HttpServletResponse) servletResponse).sendError(403, ErrorMessage.JWT_TOKEN_IS_EXPIRED);
                    }
                }
            } catch (JwtAuthenticationExeption e) {

            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
