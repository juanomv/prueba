package com.juan.prueba_back.services.implementation;

import com.juan.prueba_back.Repository.UsuarioR;
import com.juan.prueba_back.auht.AuthResponse;
import com.juan.prueba_back.auht.LoginRequest;
import com.juan.prueba_back.auht.RegisterRequest;
import com.juan.prueba_back.dao.IPersonaDao;
import com.juan.prueba_back.dao.IUsuarioDao;
import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Usuario;
import com.juan.prueba_back.security.CustomerDetailSevice;
import com.juan.prueba_back.security.jwt.JwtUtil;
import com.juan.prueba_back.services.Interface.IAuthService;
import com.juan.prueba_back.services.Interface.IUsuarioService;
import com.juan.prueba_back.utils.Error;
import com.juan.prueba_back.utils.HashPass;
import com.juan.prueba_back.utils.Token;
import com.juan.prueba_back.utils.Validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class AuthServiceImpl implements IAuthService {
    private HashPass hash = new HashPass();
    private Validator validator = new Validator();
    private Token token = new Token();


    @Autowired
    private UsuarioR usuarioRepository;

    @Autowired
    private CustomerDetailSevice customerDetailSevice;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioServiceImp usuarioService;

    @Autowired
    IUsuarioDao usuarioDao;

    @Autowired
    IPersonaDao personaDao;


    @Override
    public ResponseEntity<?> login(LoginRequest request) {

        String userd = request.getUsername();
        String password =  request.getPassword();
        Usuario user;

        if(validator.isEmail(userd)){
            user = usuarioRepository.findByEmail(userd);
        } else {
            user = usuarioRepository.findByUsername(userd);
        }


        if (user != null && hash.matchPasswords(password,user.getPassword())) {
            if (!user.getStatus()) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUserName(),password)
                );
                return ResponseEntity.ok( AuthResponse.builder().token(jwtUtil.generateToken(user.getUserName(),"admin")).build());
            }
            if (user.getSessionActive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Error("Ya iniciaste sesion en otro ordenador"));
            }
            user.setSessionActive(true);
            System.out.println("usueueuuekjsd ------ "+user);
            usuarioService.save(user);
            return ResponseEntity.ok( AuthResponse.builder().token(jwtUtil.generateToken(user.getUserName(),"admin")).build());


        } else {
            // Usuario no encontrado o la contraseña no coincide
            if(user == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Error("Usuario "+userd+" no existe!"));
            user.setIntentos(user.getIntentos()+1);
            usuarioService.save(user);
            if (user.getIntentos()>=3) {
                user.setStatus(false);
                usuarioService.save(user);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Error("Usuario "+userd+" fue bloqueado!"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Error("Usuario o contraseña no son correctos vuelva a intentar  (intentos restantes: "+(user.getIntentos()-3)+" )"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> register(RegisterRequest request) {
        Persona persona = new Persona();
        Usuario usuario = new Usuario();
        Validator validator = new Validator();
        HashPass hash = new HashPass();
        Usuario us = usuarioRepository.findByUsername(request.getUsername());
        if(us!=null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Error("El usuername "+request.getUsername()+" ya esta registrado intente con otro"));
        }
        persona.setNombres(request.getNombre());
        persona.setApellidos(request.getApellido());
        persona.setIdentificacion(request.getIdentificacion());
        persona.setFechaNacimiento(request.getFechaNacimiento());


        personaDao.save(persona);

        String email;
        email = validator.generatMail(request.getNombre(),request.getApellido(),0);
        int countmail = usuarioRepository.coutnMail(email);


        if(countmail>0) email =validator.generatMail(request.getNombre(),request.getApellido(),countmail);

        usuario.setUserName(request.getUsername());
        usuario.setPassword(hash.encodePassword(request.getPassword()));
        usuario.setMail(email);
        usuario.setPersona_idPersona2(persona.getIdPersona());

        usuarioDao.save(usuario);

        return ResponseEntity.ok( new Error("Usuario registrado corectamente"));
    }

    @Override
    public String getToken(Usuario usuario) {

        return token.getTokenD(new HashMap<>(),usuario);
    }

    @Override
    public ResponseEntity<?> logout( String Token) {
        String username = null;
        if(Token != null && Token.startsWith("Bearer ")){
            String token1 = Token.substring(7);
            username = jwtUtil.extractUsername(token1);
        }
        Usuario user = usuarioRepository.findByUsername(username);
        user.setSessionActive(false);
        usuarioDao.save(user);
        return ResponseEntity.ok( new Error("Session Cerrada"));
    }


}
