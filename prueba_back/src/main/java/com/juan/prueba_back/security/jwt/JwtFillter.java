package com.juan.prueba_back.security.jwt;

import com.juan.prueba_back.Repository.UsuarioR;
import com.juan.prueba_back.model.Usuario;
import com.juan.prueba_back.security.CustomerDetailSevice;
import com.juan.prueba_back.services.Interface.IUsuarioService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFillter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtil jwtUtil;

    @Autowired
    private CustomerDetailSevice customerDetailSevice;

    @Autowired
    private UsuarioR usuarioR;

    @Autowired
    private IUsuarioService usuarioService;
    Claims claims =  null;
    private String username=null;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().matches("/api/v1/auth/login|/api/v1/auth/register")){
            filterChain.doFilter(request,response);
        }else {
            String authozatiionHeader = request.getHeader("Authorization");
            String token = null;
            System.out.println("tokennnskljsdalkd ---- "+authozatiionHeader);
            if(authozatiionHeader != null && authozatiionHeader.startsWith("Bearer ")){
                token = authozatiionHeader.substring(7);
                username = jwtUtil.extractUsername(token);
                claims=jwtUtil.extraAllClains(token);

            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserDetails usuerDetails  =  customerDetailSevice.loadUserByUsername(username);
                System.out.println("tokennnskljsdalkd ---- "+username);
                if(jwtUtil.validateToke(token,usuerDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(usuerDetails,null,usuerDetails.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else {
                    Usuario usuario = usuarioR.findByUsername(username);
                    usuario.setSessionActive(false);
                    usuarioService.update(usuario,usuario.getIdUsuario());
                }

            }
            filterChain.doFilter(request,response);
        }
    }

    public Boolean isAdmin(){
        return  "admin".equalsIgnoreCase((String) claims.get("role"));
    }
    public Boolean isUser(){
        return  "user".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser(){
        return  username;
    }
}
