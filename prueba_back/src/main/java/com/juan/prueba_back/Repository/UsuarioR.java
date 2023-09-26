package com.juan.prueba_back.Repository;

import com.juan.prueba_back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioR extends JpaRepository<Usuario, Long> {
    @Query(value = "SELECT * FROM usuarios WHERE mail = :mail", nativeQuery = true)
    Usuario findByEmail(String mail);

    @Query(value = "SELECT * FROM usuarios WHERE user_name = :username", nativeQuery = true)
    Usuario findByUsername(String username);

    @Query(value = "SELECT count(*) FROM usuarios WHERE mail = :mail", nativeQuery = true)
    int coutnMail(String mail);
}
