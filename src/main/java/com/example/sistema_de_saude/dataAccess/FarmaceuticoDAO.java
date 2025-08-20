package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Farmaceutico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class FarmaceuticoDAO {


    private static FarmaceuticoDAO instance;
    protected EntityManager entityManager;
    public static FarmaceuticoDAO getInstance() {
        if (instance == null) {
            instance = new FarmaceuticoDAO();
        }
        return instance;
    }
    private FarmaceuticoDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Farmaceutico getById(int id) {
        return entityManager.find(Farmaceutico.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Farmaceutico> findAll() {
        return entityManager.createQuery("FROM Farmaceutico").getResultList();
    }
    public void persist(Farmaceutico farmaceutico) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(farmaceutico);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Farmaceutico farmaceutico) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(farmaceutico);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Farmaceutico farmaceutico) {
        try {
            entityManager.getTransaction().begin();
            farmaceutico = entityManager.find(Farmaceutico.class, farmaceutico.getId());
            entityManager.remove(farmaceutico);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            Farmaceutico farmaceutico = getById(id);
            remove(farmaceutico);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

