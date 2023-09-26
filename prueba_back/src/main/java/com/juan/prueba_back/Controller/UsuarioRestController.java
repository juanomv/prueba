package com.juan.prueba_back.Controller;


import com.juan.prueba_back.Repository.UsuarioR;
import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Usuario;
import com.juan.prueba_back.services.Interface.IUsuarioService;
import com.juan.prueba_back.utils.Error;
import com.juan.prueba_back.utils.Message;
import com.juan.prueba_back.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {

    private Validator validator = new Validator();

    @Autowired
    private UsuarioR usuarioRepository;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/get")
    public ResponseEntity<List<Usuario>> search() {
        return usuarioService.search();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> searchById(@PathVariable Long id) {
        return usuarioService.searchById(id);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody Usuario usuario, @PathVariable Long id) {
        return usuarioService.update(usuario, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        return usuarioService.deleteById(id);
    }

    @GetMapping("/{idUsuario}/personas")
    public ResponseEntity<List<Persona>> findPersonasByIdUsuario(@PathVariable Long idUsuario) {
        return usuarioService.findPersonasByIdUsuario(idUsuario);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        String userd = data.get("user");
        String password = data.get("password");
        Usuario user = new Usuario();

        if(validator.isEmail(userd)){
            user = usuarioRepository.findByEmail(userd);
        } else {
            user = usuarioRepository.findByUsername(userd);
        }


        if (user != null && user.getPassword().equals(password)) {
            if (!user.getStatus()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Error("Usuario bloqueado, comuniquese con Administrador"));
            }
            if (user.getSessionActive()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Error("Ya iniciaste sesion en otro ordenador"));
            }
            user.setSessionActive(true);
            usuarioService.save(user);
            return ResponseEntity.ok(user);


        } else {
            // Usuario no encontrado o la contraseña no coincide
            user.setIntentos(user.getIntentos()+1);
            usuarioService.save(user);
            if (user.getIntentos()>=3) {
                user.setStatus(false);
                usuarioService.save(user);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Error("Usuario "+userd+" fue bloqueado!"));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Error("Usuario o contraseña no son correctos vuelva a intentar  (intentos restantes: "+(user.getIntentos()-3)+" )"));
        }



    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> data){
        String email = data.get("mail");
        usuarioService.logout(email);
        return ResponseEntity.ok(new Message("Cerraste Sesion con exito!"));
    }
}
