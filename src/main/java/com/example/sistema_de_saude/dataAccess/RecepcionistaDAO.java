package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.entity.Recepcionista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class RecepcionistaDAO {


    private static RecepcionistaDAO instance;
    protected EntityManager entityManager;
    public static RecepcionistaDAO getInstance() {
        if (instance == null) {
            instance = new RecepcionistaDAO();
        }
        return instance;
    }
    private RecepcionistaDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Recepcionista getById(int id) {
        return entityManager.find(Recepcionista.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Recepcionista> findAll() {
        return entityManager.createQuery("FROM Recepcionista").getResultList();
    }
    public void persist(Recepcionista recepcionista, Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.persist(recepcionista);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Recepcionista recepcionista) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(recepcionista);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Recepcionista recepcionista) {
        try {
            entityManager.getTransaction().begin();
            recepcionista = entityManager.find(Recepcionista.class, recepcionista.getId());
            entityManager.remove(recepcionista);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            Recepcionista recepcionista = getById(id);
            remove(recepcionista);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

