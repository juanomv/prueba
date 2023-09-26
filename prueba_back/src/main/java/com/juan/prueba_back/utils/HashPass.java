package com.juan.prueba_back.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HashPass {
    private final PasswordEncoder passwordEncoder;

    public HashPass( ) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public String encodePassword(String rawPassword) {
        // Codificar una contraseña
        String encodedPassword = passwordEncoder.encode(rawPassword);
        return encodedPassword;
    }

    public boolean matchPasswords(String rawPassword, String encodedPassword) {
        // Verificar si una contraseña sin codificar coincide con una contraseña codificada
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
