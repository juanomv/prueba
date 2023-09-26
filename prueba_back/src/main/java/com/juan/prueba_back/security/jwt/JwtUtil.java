package com.juan.prueba_back.security.jwt;

import com.juan.prueba_back.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    String secret = "1234567890abcdefghijklmnopqrstuxyzABCDEFGHIJKLMNOPQRSTUVWXYZ9565pioAE6WERTTGHTTTTTRFDDDD";

    public String extractUsername(String token){
        return extraClains(token,Claims::getSubject);

    }

    public Date extractExpiration(String token){
        return extraClains(token,Claims::getExpiration);
    }

    public <T> T extraClains(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extraAllClains(token);
        return claimsResolver.apply(claims);
    }

    public Claims extraAllClains(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String role){
        Map<String,Object> claims = new HashMap<>();
        claims.put("role",role);
        return createToken(claims,username);
    }

    public  String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public boolean validateToke(String token, UserDetails usuario){
        final  String username = extractUsername(token);
        return  (username.equals(usuario.getUsername())) && !isTokenExpired(token);
    }
}
