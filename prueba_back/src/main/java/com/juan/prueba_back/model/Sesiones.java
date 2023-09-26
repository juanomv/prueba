package com.juan.prueba_back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Sessiones")
public class Sesiones implements Serializable {

    @Id
    @Column(name = "usuarios_idUsuarios")
    private Long IdUsuario;
    @Column(name = "FechaInicio")
    private Date fechaInicio;
    @Column(name = "FechaCierre")
    private Date fechaCierre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuarios_idUsuarios")
    private List<Usuario> usuarios;
}
