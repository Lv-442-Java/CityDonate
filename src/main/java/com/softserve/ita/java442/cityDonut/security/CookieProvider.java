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

    public List<Cookie> createCookie(String key,String token) {
        Cookie cookie = new Cookie(key, token);
        cookie.setMaxAge(EXPIRED_TIME_FOR_COOKIE);
        return cookie;
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
    private List<Cookie> createList(Cookie ...k){
        List<Cookie> list = new ArrayList<>();
        for(Cookie cookies : k){
            list.add(cookies);
        }
        return list;
    }
}
