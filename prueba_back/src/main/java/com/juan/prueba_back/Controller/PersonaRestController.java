package com.juan.prueba_back.Controller;

import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.services.Interface.IPersonaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/personas")
public class PersonaRestController {


    @Autowired
    private IPersonaService personaService;

    @GetMapping("/get")
    public ResponseEntity<List<Persona>> search() {
        return personaService.search();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Persona> searchById(@PathVariable Long id) {
        return personaService.searchById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> save(@RequestBody Persona persona) {
        return personaService.save(persona);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Persona> update(@PathVariable Long id, @RequestBody Persona persona) {

        return personaService.update(persona, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Persona> deleteById(@PathVariable Long id) {
        return personaService.deleteById(id);
    }
}

