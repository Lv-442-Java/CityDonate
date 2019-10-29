package com.softserve.ita.java442.cityDonut.security.congiquration;

import com.softserve.ita.java442.cityDonut.model.Role;
import com.softserve.ita.java442.cityDonut.model.User;
import exeption.JwtAuthenticationExeption;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private String SECRET ="CityDonut";
    private long VALIDATION_TIME =3600000;
    private Date CURRENT_DATE ;
    private Date EXPIRATION_DATA;

    private UserDetailsService userDetailsService;
    @PostConstruct
    protected void init(){SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    public String createToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles",user.getRole().getRole());
        CURRENT_DATE  = new Date();
        EXPIRATION_DATA = new Date(CURRENT_DATE.getTime() +VALIDATION_TIME );

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(CURRENT_DATE)
                .setExpiration(EXPIRATION_DATA)
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
    }

    public Authentication getAuthentication(String token){
       UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return  new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer "))
            return  token.substring(7,token.length());
        return  null;
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey("SECRET").parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(CURRENT_DATE))
                return false;
            return true;
        }catch(JwtException e){
            throw new JwtAuthenticationExeption("JWT token is invalid !");
        }
    }

}
