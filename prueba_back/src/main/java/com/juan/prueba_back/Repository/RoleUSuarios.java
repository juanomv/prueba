package com.juan.prueba_back.Repository;

import com.juan.prueba_back.model.Usuario;
import org.springframework.data.jpa.repository.Query;

public interface RoleUSuarios {
    @Query(value = "INSERT INTO rol_usuario values (:rol,:usuario)", nativeQuery = true)
    Usuario InsertRolUsuario(Long rol, Long usuario);

    @Query(value = "select * from rol_usuario values (:rol,:usuario)", nativeQuery = true)
    Usuario getRolesUsuario(Long rol, Long usuario);
}
