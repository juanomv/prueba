package com.juan.prueba_back.security;

import com.juan.prueba_back.Repository.UsuarioR;
import com.juan.prueba_back.dao.IPersonaDao;
import com.juan.prueba_back.dao.IUsuarioDao;
import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Usuario;
import com.juan.prueba_back.services.Interface.IUsuarioService;
import com.juan.prueba_back.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class CustomerDetailSevice  implements UserDetailsService {

    @Autowired
    private UsuarioR usuarioRepository;


    private IUsuarioService usuarioService;

    @Autowired
    IUsuarioDao usuarioDao;

    @Autowired
    IPersonaDao personaDao;

    private  Usuario user;

    private Validator validator;


    public  Usuario getUserDail(){
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(validator.isEmail(username)){
            user = usuarioRepository.findByEmail(username);
        } else {
            user = usuarioRepository.findByUsername(username);
        }

        if(!Objects.isNull(user)){
            return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("usuario no encontrado");
        }
    }
}
