package com.juan.prueba_back.Controller;

import com.juan.prueba_back.model.Rol;
import com.juan.prueba_back.services.Interface.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/v1/rol")
public class RolController {
    @Autowired
    private IRolService rolService;

    @GetMapping("/get")
    public ResponseEntity<List<Rol>> getRoles() {
        return rolService.search();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRolById(@PathVariable Long id) {
        return rolService.searchById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createRol(@RequestBody Rol rol) {
        return rolService.save(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRol(@RequestBody Rol rol, @PathVariable Long id) {
        return rolService.update(rol, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRol(@PathVariable Long id) {
        return rolService.deleteById(id);
    }
}
