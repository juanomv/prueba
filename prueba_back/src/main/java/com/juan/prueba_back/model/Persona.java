package com.juan.prueba_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "Persona")
public class Persona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPersona;
    @Column(name = "Nombres",length = 60 , columnDefinition = "CHAR(60)")
    private String nombres;
    @Column(name = "Apellidos",length = 60, columnDefinition = "CHAR(60)")
    private String apellidos;
    @Column(name = "Identificacion",length = 120, columnDefinition = "CHAR(120)")
    private String identificacion;
    @Column(name = "FechaNacimiento", columnDefinition = "DATE")
    private Date fechaNacimiento;

}
