package com.juan.prueba_back.dao;

import com.juan.prueba_back.model.Persona;

import org.springframework.data.repository.CrudRepository;

public interface IPersonaDao extends CrudRepository<Persona,Long> {
}
