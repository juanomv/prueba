package com.juan.prueba_back.dao;

import com.juan.prueba_back.model.Rol;

import org.springframework.data.repository.CrudRepository;

public interface IRolDao extends CrudRepository<Rol,Long> {
}
