package com.example.sistema_de_saude.util;

import org.mindrot.jbcrypt.BCrypt;

public class HashSenha {


    // Gera hash para uma senha
    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    // Verifica se a senha bate com o hash
    public static boolean verificarSenha(String senha, String hash) {
        if(hash == null || hash.isEmpty()) return false;
        return BCrypt.checkpw(senha, hash);
    }
}
