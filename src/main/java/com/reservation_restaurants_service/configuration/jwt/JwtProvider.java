package com.reservation_restaurants_service.configuration.jwt;

import com.reservation_restaurants_service.entity.Role;
import com.reservation_restaurants_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

 //   @Value("${jwt.token.secret}")
    private  String jwtSecret = "jwtSecret";

   // @Value("${jwt.token.expired}")
    private long jwtExpirationInMs;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }
    public String generationToken(String email, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", getUserRoleNamesFromJWT(roles));

        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay().toInstant(ZoneOffset.UTC));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    private List<String> getUserRoleNamesFromJWT(List<Role> roles){
        List<String> result = new ArrayList<>();
        roles.forEach(role -> result.add(role.getTypeOfRole()));
        return result;
    }

    public boolean validatorToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            e.getMessage();
        }
        return false;
    }

    public String getLoginFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public  String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer_")){
            return bearerToken.substring(7);
        }
        return null;
    }
}