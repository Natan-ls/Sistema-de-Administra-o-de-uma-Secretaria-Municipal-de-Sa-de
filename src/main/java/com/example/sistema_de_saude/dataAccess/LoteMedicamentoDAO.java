package com.example.sistema_de_saude.dataAccess;


import com.example.sistema_de_saude.entity.LoteMedicamento;
import com.example.sistema_de_saude.entity.Protocolo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class LoteMedicamentoDAO {


    private static LoteMedicamentoDAO instance;
    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public static LoteMedicamentoDAO getInstance() {
        if (instance == null) {
            instance = new LoteMedicamentoDAO();
        }
        return instance;
    }
    private LoteMedicamentoDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public LoteMedicamento getById(int id) {
        return entityManager.find(LoteMedicamento.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<LoteMedicamento> findAll() {
        return entityManager.createQuery("FROM LoteMedicamento").getResultList();
    }
    public void persist(LoteMedicamento loteMedicamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(loteMedicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(LoteMedicamento loteMedicamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(loteMedicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(LoteMedicamento loteMedicamento) {
        try {
            entityManager.getTransaction().begin();
            loteMedicamento = entityManager.find(LoteMedicamento.class, loteMedicamento.getId());
            entityManager.remove(loteMedicamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(int id) {
        try {
            LoteMedicamento loteMedicamento = getById(id);
            remove(loteMedicamento);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void atualizarEntrega(Protocolo protocolo, List<LoteMedicamento> lotesParaAtualizar) {
        try {
            entityManager.getTransaction().begin();

            // Atualiza todos os lotes
            for (LoteMedicamento lote : lotesParaAtualizar) {
                entityManager.merge(lote);
            }

            // Atualiza protocolo (status e dataEntrega)
            entityManager.merge(protocolo);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }


}
