package com.juan.prueba_back.services.Interface;


import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Rol;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRolService {
    public ResponseEntity<List<Rol>> search();
    public ResponseEntity<Object> searchById(Long id);
    public ResponseEntity<Object> save(Rol rol);
    public ResponseEntity<Object> update(Rol rol,Long id);
    public ResponseEntity<Object> deleteById(Long id);
    public ResponseEntity<List<Persona>> findPersonasByIdUsuario(Long idRol);

}
