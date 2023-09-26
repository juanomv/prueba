package com.juan.prueba_back.services.implementation;

import com.juan.prueba_back.dao.IRolDao;

import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Rol;
import com.juan.prueba_back.services.Interface.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements IRolService {
    @Autowired
    private IRolDao rolDao;

    @Override
    public ResponseEntity<List<Rol>> search() {
        List<Rol> roles =  (List<Rol>) rolDao.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> searchById(Long id) {
        Optional<Rol> rol = rolDao.findById(id);
        if (rol.isPresent()) {
            return new ResponseEntity<>(rol.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("El rol no se encontró.", HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<Object> save(Rol rol) {
        try {
            Rol nuevoRol = rolDao.save(rol);
            return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo crear el rol.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> update(Rol rol, Long id) {
        Optional<Rol> rolExistente = rolDao.findById(id);
        if (rolExistente.isPresent()) {
            Rol rolActualizado = rolExistente.get();
            rolActualizado.setRolName(rol.getRolName());
            // TODO: agergar actulizcciones
            rolDao.save(rolActualizado);
            return new ResponseEntity<>("Rol actualizado exitosamente.", HttpStatus.OK);
        }
        return new ResponseEntity<>("El rol no se encontró.", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> deleteById(Long id) {
        Optional<Rol> rolExistente = rolDao.findById(id);
        if (rolExistente.isPresent()) {
            rolDao.deleteById(id);
            return new ResponseEntity<>("Rol eliminado exitosamente.", HttpStatus.OK);
        }
        return new ResponseEntity<>("El rol no se encontró.", HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<List<Persona>> findPersonasByIdUsuario(Long idRol) {
        return null;
    }
}
