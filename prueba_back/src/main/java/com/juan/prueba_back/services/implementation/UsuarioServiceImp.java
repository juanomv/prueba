package com.juan.prueba_back.services.implementation;

import com.juan.prueba_back.Repository.UsuarioR;
import com.juan.prueba_back.dao.IUsuarioDao;
import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Usuario;


import com.juan.prueba_back.services.Interface.IUsuarioService;
import com.juan.prueba_back.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioServiceImp implements IUsuarioService {

    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private UsuarioR usuarioRepository;

    private Validator validator;
    private  Usuario usuario;
    @Override
    public ResponseEntity<List<Usuario>>search() {
        try {
            List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
            return new ResponseEntity<>(usuarios, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            String errorMessage = "Ocurrió un error al buscar usuarios: " + e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> searchById(Long id) {
        try {
            Optional<Usuario> usuario = usuarioDao.findById(id);
            if (usuario.isPresent()) {
                return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String errorMessage = "Ocurrió un error al buscar el usuario: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> save(Usuario usuario) {
        try {
            Usuario savedUsuario = usuarioDao.save(usuario);
            return new ResponseEntity<>(savedUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Ocurrió un error al guardar el usuario: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> update(Usuario usuario, Long id) {
        try {
            Optional<Usuario> optionalUsuario = usuarioDao.findById(id);
            if (optionalUsuario.isPresent()) {
                Usuario existingUsuario = optionalUsuario.get();
                existingUsuario.setUserName(usuario.getUserName());
                existingUsuario.setPassword(usuario.getPassword());
                // Actualiza otros campos según sea necesario

                Usuario updatedUsuario = usuarioDao.save(existingUsuario);
                return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String errorMessage = "Ocurrió un error al actualizar el usuario: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) {
        try {
            Optional<Usuario> optionalUsuario = usuarioDao.findById(id);
            if (((Optional<?>) optionalUsuario).isPresent()) {
                usuarioDao.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String errorMessage = "Ocurrió un error al eliminar el usuario: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<Persona>> findPersonasByIdUsuario(Long idUsuario) {
        try {
            List<Persona> personas = usuarioDao.findPersonasByIdUsuario(idUsuario);
            return new ResponseEntity<>(personas, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Ocurrió un error al buscar las personas relacionadas: " + e.getMessage();
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> logear(String user, String pass) {
        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByEmail(user));
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }



    @Override
    public ResponseEntity<Object> logout(String email) {
        return null;
    }


}
