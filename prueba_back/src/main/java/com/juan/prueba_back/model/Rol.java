package com.juan.prueba_back.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "Rol")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    @Column(name = "RolName",columnDefinition = "CHAR(50)")
    private String rolName;
    @ManyToMany(mappedBy = "roles")
    private Set<RolOpciones> rolesOpciones;

    @ManyToMany(mappedBy = "roles")
    private Set<Usuario> usarios;
}
