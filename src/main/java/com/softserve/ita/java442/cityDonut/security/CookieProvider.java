package com.softserve.ita.java442.cityDonut.security;

import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class CookieProvider {
    public Cookie createCookie(String token) {
        return new Cookie("JWT", token);
    }

    public String readCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("JWT"))
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
