package com.juan.prueba_back.services.Interface;

import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.RolOpciones;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRolOpcionesService {
    public ResponseEntity<List<RolOpciones>> search();
    public ResponseEntity<RolOpciones> searchById(Long id);
    public ResponseEntity<Object> save(RolOpciones rolOpciones);
    public ResponseEntity<RolOpciones> update(RolOpciones rolOpciones,Long id);
    public ResponseEntity<RolOpciones> deleteById(Long id);
}



