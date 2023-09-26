package com.juan.prueba_back.services.implementation;

import com.juan.prueba_back.dao.IRolDao;
import com.juan.prueba_back.dao.IRolOpcionesDao;
import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Rol;
import com.juan.prueba_back.model.RolOpciones;
import com.juan.prueba_back.services.Interface.IRolOpcionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolOpcionesImpl implements IRolOpcionesService {
    @Autowired
    private IRolOpcionesDao rolOpcionesDao;
    @Override
    public ResponseEntity<List<RolOpciones>> search() {
        List<RolOpciones> roles =  (List<RolOpciones>) rolOpcionesDao.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RolOpciones> searchById(Long id) {
        Optional<RolOpciones> opcion = rolOpcionesDao.findById(id);
        if (opcion.isPresent()) {
            return new ResponseEntity<>(opcion.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<Object> save(RolOpciones rolOpciones) {
        try {
            RolOpciones nuevaOpcion = rolOpcionesDao.save(rolOpciones);
            return new ResponseEntity<>(nuevaOpcion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo crear la opción de rol.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<RolOpciones> update(RolOpciones rolOpciones, Long id) {
        Optional<RolOpciones> opcionExistente = rolOpcionesDao.findById(id);
        if (opcionExistente.isPresent()) {
            RolOpciones opcionActualizada = opcionExistente.get();
            opcionActualizada.setNombreOpcion(rolOpciones.getNombreOpcion());
            // Actualiza los campos según tus necesidades
            rolOpcionesDao.save(opcionActualizada);
            return new ResponseEntity<>(opcionActualizada, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<RolOpciones> deleteById(Long id) {
        return null;
    }
}
