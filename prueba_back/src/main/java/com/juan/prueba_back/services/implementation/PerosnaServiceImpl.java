package com.juan.prueba_back.services.implementation;


import com.juan.prueba_back.dao.IPersonaDao;
import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.services.Interface.IPersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PerosnaServiceImpl implements IPersonaService {
    @Autowired
    private IPersonaDao personaDao;
    @Override
    public ResponseEntity<List<Persona>> search() {
        List<Persona> personas = new ArrayList<Persona>();
        try {
            personas = (List<Persona>) personaDao.findAll();
            return new ResponseEntity<>(personas, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            // Manejar la excepción y devolver una respuesta apropiada
            String errorMessage = "Ocurrió un error al buscar personas: " + e.getMessage();
            return new ResponseEntity<>(personas, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

    @Override
    public ResponseEntity<Persona> searchById(Long id) {
        try {
            // Buscar la persona por su ID utilizando personaDao o el repositorio correspondiente
            Optional<Persona> persona = personaDao.findById(id);

            if (persona.isPresent()) {
                // Si se encuentra la persona, devolverla en un ResponseEntity con HttpStatus OK (200)
                return new ResponseEntity<>(persona.get(), HttpStatus.OK);
            } else {
                // Si no se encuentra la persona, devolver un ResponseEntity con HttpStatus NOT FOUND (404)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Manejar la excepción y devolver una respuesta de error con HttpStatus INTERNAL SERVER ERROR (500)
            String errorMessage = "Ocurrió un error al buscar la persona: " + e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> save(Persona persona) {
        try {
            Persona savedPersona = personaDao.save(persona);
            return new ResponseEntity<>(savedPersona, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            // Si ocurre una excepción relacionada con la integridad de los datos
            // (por ejemplo, la longitud del apellido es demasiado larga), maneja el error aquí
            return new ResponseEntity<>("Error: Los datos proporcionados no son válidos.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Maneja otras excepciones de manera adecuada
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Persona> update(Persona persona, Long id) {
        try {
            System.out.println("esta entrado");
            Optional<Persona> personaOptional = personaDao.findById(id);
            if (personaOptional.isPresent()) {
                Persona existingPersona = personaOptional.get();
                existingPersona.setNombres(persona.getNombres());
                existingPersona.setApellidos(persona.getApellidos());
                // Actualiza otros campos según sea necesario

                Persona updatedPersona = personaDao.save(existingPersona);
                return new ResponseEntity<>(updatedPersona, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // La persona no existe
            }
        } catch (Exception e) {
            // Manejo de errores si la actualización falla
            throw e;
            /*return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            */
        }

    }

    @Override
    public ResponseEntity<Persona> deleteById(Long id) {
        try {
            Optional<Persona> personaOptional = personaDao.findById(id);
            if (personaOptional.isPresent()) {
                personaDao.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Éxito en la eliminación
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // La persona no existe
            }
        } catch (Exception e) {
            // Manejo de errores si la eliminación falla
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

