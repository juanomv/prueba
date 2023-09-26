package com.juan.prueba_back.services.Interface;

import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IUsuarioService {
    public ResponseEntity<List<Usuario>> search();
    public ResponseEntity<Object> searchById(Long id);
    public ResponseEntity<Object> save(Usuario usuario);
    public ResponseEntity<Object> update(Usuario usuario,Long id);
    public ResponseEntity<Object> deleteById(Long id);
    public ResponseEntity<List<Persona>> findPersonasByIdUsuario(Long idUsuario);
    public ResponseEntity<Object>  logear(String user,String pass );

    public ResponseEntity<Object> logout(String email);
}
