package com.juan.prueba_back.utils;

import com.juan.prueba_back.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class Token {
    private static final String SECRET_KEY = "dskjfwjkdhsakjdlhsdjkfhskdjadskfj";
    public String getTokenD(Map<String,Object> extraClains, Usuario usuario){
        return Jwts.builder()
                .setClaims(extraClains)
                .setSubject(usuario.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                .signWith(getKey())
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return  Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String getUserFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();

    }

    public  boolean isTokenValid(String token, Usuario usuario) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            if((getUserFromToken(token)).equals(usuario.getUserName()) && !isExpired(token)) return true;
            return false;
        } catch (ExpiredJwtException | SignatureException ex) {
            // El token es inválido debido a la expiración o firma incorrecta
            return false;
        } catch (Exception ex) {
            // Manejar otras excepciones si es necesario
            return false;

        }
}
    // Método para verificar si un token JWT ha expirado
    public static boolean isExpired(String token) {
        try {
            Claims claims = Jwts.parser().parseClaimsJws(token).getBody();
            // Obtén la fecha de expiración del token
            long expirationMillis = claims.getExpiration().getTime();
            // Obtén el tiempo actual en milisegundos
            long currentTimeMillis = System.currentTimeMillis();
            // Compara la fecha de expiración con el tiempo actual
            return expirationMillis < currentTimeMillis;
        } catch (Exception e) {
            // Si ocurre alguna excepción, el token se considera inválido
            return true;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeder = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeder) && authHeder.startsWith("Bearer ")) {
            return authHeder.substring(7);
        }
        return null;
    }
}