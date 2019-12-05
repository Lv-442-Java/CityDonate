package com.softserve.ita.java442.cityDonut.security;

import com.softserve.ita.java442.cityDonut.constant.ErrorMessage;
import com.softserve.ita.java442.cityDonut.model.User;
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
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRED_TIME_ACCESS_TOKEN))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateAccessToken(long id, String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", role);
        claims.put("id", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 6000000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRED_TIME_REFRESH_TOKEN))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails,
                "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public JwtToken UserData(String token) {
        return JwtToken.builder()
                .email(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject())
                .role( (String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("roles"))
                .id((int) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("id"))
                .build();
    }

    public String resolveToken(HttpServletRequest request, String key) {
        return cookieProvider.readCookie(request, key);
    }

    public boolean validateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch(JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationExeption(ErrorMessage.JWT_TOKEN_IS_EXPIRED);
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
