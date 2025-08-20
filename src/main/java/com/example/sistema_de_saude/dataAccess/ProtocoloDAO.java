package com.example.sistema_de_saude.dataAccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class ProtocoloDAO {


    private static ProtocoloDAO instance;
    protected EntityManager entityManager;
    public static ProtocoloDAO getInstance() {
        if (instance == null) {
            instance = new ProtocoloDAO();
        }
        return instance;
    }
    private ProtocoloDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Protocolo getById(int id) {
        return entityManager.find(Protocolo.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Protocolo> findAll() {
        return entityManager.createQuery("FROM Protocolo").getResultList();
    }
    public void persist(Protocolo protocolo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(protocolo);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Protocolo protocolo) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(protocolo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Protocolo protocolo) {
        try {
            entityManager.getTransaction().begin();
            protocolo = entityManager.find(Protocolo.class, protocolo.getId());
            entityManager.remove(protocolo);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            Protocolo protocolo = getById(id);
            remove(protocolo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

