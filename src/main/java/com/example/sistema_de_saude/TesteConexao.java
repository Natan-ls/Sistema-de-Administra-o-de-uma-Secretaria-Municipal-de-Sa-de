package com.example.sistema_de_saude;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class TesteConexao {

    public static void main(String[] args) {
        System.out.println("Iniciando teste de conexão com o banco de dados...");

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            // 1. Criar EntityManagerFactory
            System.out.println("Criando EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("sistemasaudePU");

            // 2. Criar EntityManager
            System.out.println("Criando EntityManager...");
            em = emf.createEntityManager();

            // 3. Testar conexão
            System.out.println("Testando conexão com o banco...");
            boolean connected = em.createNativeQuery("SELECT 1").getSingleResult().equals(1);

            if (connected) {
                System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
                System.out.println("Configuração JPA funcionando corretamente.");
            } else {
                System.out.println("⚠️ Conexão estabelecida, mas teste de consulta falhou.");
            }

        } catch (Exception e) {
            System.err.println("❌ Erro ao conectar ao banco de dados:");
            e.printStackTrace();

            // Verificar causas específicas
            if (e.getCause() != null) {
                System.err.println("\nCausa raiz:");
                e.getCause().printStackTrace();
            }
        } finally {
            // 4. Fechar recursos
            if (em != null && em.isOpen()) {
                em.close();
                System.out.println("EntityManager fechado.");
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
                System.out.println("EntityManagerFactory fechado.");
            }
        }
    }
}