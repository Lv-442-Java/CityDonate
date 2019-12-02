package com.softserve.ita.java442.cityDonut.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class CookieProvider {
    @Value("${expired.time.for.cookie}")
    private int EXPIRED_TIME_FOR_COOKIE;

    public Cookie createCookie(String key,String token) {
        Cookie cookie = new Cookie(key, token);
        cookie.setMaxAge(EXPIRED_TIME_FOR_COOKIE);
        cookie.setPath("/");
        return cookie;
    }


    public String readCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(key))
                    token = c.getValue();
            }
        }
        return token;
    }

    public Cookie deleteCookie() {
        Cookie cookie = new Cookie("JWT", "");
        cookie.setMaxAge(0);
        return cookie;
    }
}
