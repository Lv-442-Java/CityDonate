package com.softserve.ita.java442.cityDonut.security;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.model.User;
import com.softserve.ita.java442.cityDonut.service.UserService;
import com.softserve.ita.java442.cityDonut.exception.JwtAuthenticationExeption;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {
    @Value("${secret.key.for.token}")
    private String SECRET_KEY;
    @Value("${expired.time.access.token}")
    private long EXPIRED_TIME_ACCESS_TOKEN;
    @Value("${expired.time.refresh.token}")
    private long EXPIRED_TIME_REFRESH_TOKEN;

    private JWTUserDetailsService jwtUserDetailsService;
    private CookieProvider cookieProvider;
    private Date CURRENT_DATE = new Date();

    public JWTTokenProvider() {}

    @Autowired
    public JWTTokenProvider(JWTUserDetailsService jwtUserDetailsService, CookieProvider cookieProvider) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.cookieProvider = cookieProvider;
    }

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String generateAccessToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRole().getRole());
        claims.put("id", user.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(CURRENT_DATE)
                .setExpiration(new Date(CURRENT_DATE.getTime() + EXPIRED_TIME_ACCESS_TOKEN))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(CURRENT_DATE)
                .setExpiration(new Date(CURRENT_DATE.getTime() + EXPIRED_TIME_ACCESS_TOKEN))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUserEmail(token));
        System.out.println("Auth "+userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails,
                "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request, String key) {
        return cookieProvider.readCookie(request, key);
    }

    public boolean validateToken(String token) {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())){
                throw new JwtAuthenticationExeption(ErrorMessage.JWT_TOKEN_IS_EXPIRED);
            }
            return true;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
