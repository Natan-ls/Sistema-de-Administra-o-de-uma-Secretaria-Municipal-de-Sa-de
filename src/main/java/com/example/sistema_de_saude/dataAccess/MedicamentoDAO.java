package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Medicamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class MedicamentoDAO {


    private static MedicamentoDAO instance;
    protected EntityManager entityManager;
    public static MedicamentoDAO getInstance() {
        if (instance == null) {
            instance = new MedicamentoDAO();
        }
        return instance;
    }
    private MedicamentoDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Medicamento getById(int id) {
        return entityManager.find(Medicamento.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Medicamento> findAll() {
        return entityManager.createQuery("FROM Medicamento").getResultList();
    }
    public void persist(Medicamento medicamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(medicamento);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Medicamento medicamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(medicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Medicamento medicamento) {
        try {
            entityManager.getTransaction().begin();
            medicamento = entityManager.find(Medicamento.class, medicamento.getId());
            entityManager.remove(medicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            Medicamento medicamento = getById(id);
            remove(medicamento);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

