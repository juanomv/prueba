package com.juan.prueba_back.utils;

public class Validator {



    public String generatMail(String name, String lastname,int valor) {
        String firstNameInitial = name.substring(0, 1).toLowerCase();
        String lastName = lastname.split(" ")[0].toLowerCase();
        String email;
        if(valor<=0 )return firstNameInitial + lastName  + "@mail.com";
        return firstNameInitial + lastName  + valor+ "@mail.com";

    }

    public boolean isValidIdentification(String identification) {
        for (int i = 0; i <= identification.length() - 4; i++) {
            char currentChar = identification.charAt(i);
            boolean hasRepeatedDigits = true;

            for (int j = i + 1; j < i + 4; j++) {
                if (identification.charAt(j) != currentChar) {
                    hasRepeatedDigits = false;
                    break;
                }
            }

            if (hasRepeatedDigits) {
                return false;
            }
        }

        return true;
    }
    public static boolean isEmail(String input) {
        // Expresión regular para validar un correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // Verificar si el input coincide con el patrón de un correo electrónico
        return input.matches(emailRegex);
    }
}
