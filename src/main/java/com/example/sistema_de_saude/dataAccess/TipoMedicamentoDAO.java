package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.TipoMedicamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class TipoMedicamentoDAO {


    private static TipoMedicamentoDAO instance;
    protected EntityManager entityManager;
    public static TipoMedicamentoDAO getInstance() {
        if (instance == null) {
            instance = new TipoMedicamentoDAO();
        }
        return instance;
    }
    private TipoMedicamentoDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public TipoMedicamento getById(int id) {
        return entityManager.find(TipoMedicamento.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<TipoMedicamento> findAll() {
        return entityManager.createQuery("FROM TipoMedicamento").getResultList();
    }
    public void persist(TipoMedicamento tipoMedicamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tipoMedicamento);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(TipoMedicamento tipoMedicamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(tipoMedicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(TipoMedicamento tipoMedicamento) {
        try {
            entityManager.getTransaction().begin();
            tipoMedicamento = entityManager.find(TipoMedicamento.class, tipoMedicamento.getId());
            entityManager.remove(tipoMedicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            TipoMedicamento tipoMedicamento = getById(id);
            remove(tipoMedicamento);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

