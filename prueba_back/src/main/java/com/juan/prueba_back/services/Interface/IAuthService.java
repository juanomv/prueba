package com.juan.prueba_back.services.Interface;

import com.juan.prueba_back.auht.LoginRequest;
import com.juan.prueba_back.auht.RegisterRequest;
import com.juan.prueba_back.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestHeader;

public interface IAuthService {
    public  ResponseEntity<?>  login(LoginRequest request);
    public ResponseEntity<?> register(RegisterRequest request);
    public String getToken(Usuario usuario);

    public ResponseEntity<?> logout(String token);
}
