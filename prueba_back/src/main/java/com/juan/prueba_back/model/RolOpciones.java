package com.juan.prueba_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "RolOpciones")
public class RolOpciones implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOption;
    @Column(name = "NombreOpcion", length = 50 ,columnDefinition = "CHAR(50)")
    private String nombreOpcion;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "rol_rolOpciones",
            joinColumns = @JoinColumn(name = "RolOpciones_idOpcion"),
            inverseJoinColumns = @JoinColumn(name = "Rol_idRol"))
    private Set<Rol> roles;

}
