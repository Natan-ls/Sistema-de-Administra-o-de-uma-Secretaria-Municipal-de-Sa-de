package com.example.sistema_de_saude.util;

import java.util.regex.Pattern;

public class Validador{

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"
    );

    private static final Pattern SUS_REGEX = Pattern.compile("^\\d{15}$");


    // CPF (formato XXX.XXX.XXX-XX ou apenas números)
    public static boolean cpfIsValido(String cpf) {
        if (cpf == null) return false;

        String numeros = cpf.replaceAll("\\D", "");

        if (numeros.length() != 11) return false;

        if (numeros.chars().distinct().count() == 1) return false;

        int[] peso1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] peso2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(numeros.charAt(i)) * peso1[i];
            }
            int resto = soma % 11;
            int dig1 = (resto < 2) ? 0 : 11 - resto;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(numeros.charAt(i)) * peso2[i];
            }
            resto = soma % 11;
            int dig2 = (resto < 2) ? 0 : 11 - resto;

            return dig1 == Character.getNumericValue(numeros.charAt(9)) &&
                    dig2 == Character.getNumericValue(numeros.charAt(10));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean emailIsValido(String email) {
        if (email == null) return false;
        return EMAIL_REGEX.matcher(email).matches();
    }

    public static boolean susIsValido(String sus) {
        if (sus == null) return false;
        return SUS_REGEX.matcher(sus).matches();
    }
}

