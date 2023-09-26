package com.juan.prueba_back.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    @Column(name = "UserName", columnDefinition = "CHAR(255)" ,unique = true , nullable = false)
    @NotBlank(message = "Name es un dato Obligatorio")
    @NotNull(message = "Name es un dato Obligatorio")
    @Pattern(regexp = "^[^!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?]*$", message = "El user name no debe contener signos")
    private String userName;
    @Column(name = "Password" )
    private String password;
    @Column(name = "Mail", columnDefinition = "CHAR(255)" , unique = true)
    private String mail;
    @Column(name = "SessionActive")
    private Boolean sessionActive = false;
    @Column(name = "Persona_idPersona2")
    private Long persona_idPersona2;
    @Column(name = "Status")
    private Boolean status = true;
    @Column(name = "intentosSesion" )
    private int intentos = 0;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "rol_usuarios",
            joinColumns = @JoinColumn(name = "usuarios_idUsuaios"),
            inverseJoinColumns = @JoinColumn(name = "Rol_idRol"))
    private Set<Rol> roles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Persona_idPersona2")
    private List<Persona> personas;
}
