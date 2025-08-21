package com.example.sistema_de_saude;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import com.example.sistema_de_saude.*;

public class TesteConexao {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            // 1. Criar EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("sistemasaudePU");

            // 2. Criar EntityManager
            em = emf.createEntityManager();

            // 3. Testar conexão com transação
            em.getTransaction().begin();
            Object result = em.createNativeQuery("SELECT 1").getSingleResult();
            em.getTransaction().commit();

            if (result.equals(1)) {
                System.out.println("✅ Conexão e consulta funcionando!");
            } else {
                System.out.println("⚠️ Retorno inesperado: " + result);
            }

        } catch (Exception e) {
            System.err.println("❌ Erro na conexão/consulta:");
            e.printStackTrace();

            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}