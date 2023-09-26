package com.juan.prueba_back.auht;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String nombre;
    String apellido;
    String identificacion;
    Date fechaNacimiento;
    String username;
    String password;
}

