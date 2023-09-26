package com.juan.prueba_back.Controller;

import com.juan.prueba_back.Repository.UsuarioR;
import com.juan.prueba_back.auht.AuthResponse;
import com.juan.prueba_back.auht.LoginRequest;
import com.juan.prueba_back.auht.RegisterRequest;
import com.juan.prueba_back.model.Usuario;
import com.juan.prueba_back.services.Interface.IAuthService;
import com.juan.prueba_back.services.Interface.IUsuarioService;
import com.juan.prueba_back.utils.Error;
import com.juan.prueba_back.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private Validator validator = new Validator();

    @Autowired
    private UsuarioR usuarioRepository;

    @Autowired
    private IAuthService authSevice;

    private IUsuarioService usuarioService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest data) {
        return ResponseEntity.ok(authSevice.login(data));
    }

    @PostMapping(value="/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest data){

        return ResponseEntity.ok(authSevice.register(data));
    }

    @GetMapping(value="/logout")
    public ResponseEntity<?> logaou(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){

        return ResponseEntity.ok(authSevice.logout(token));
    }
}
