package com.juan.prueba_back.dao;

import com.juan.prueba_back.model.Persona;
import com.juan.prueba_back.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IUsuarioDao extends CrudRepository<Usuario,Long> , JpaRepository<Usuario,Long> {
    List<Persona> findPersonasByIdUsuario(Long idUsuario);

}
