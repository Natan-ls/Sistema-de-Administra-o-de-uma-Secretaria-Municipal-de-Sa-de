package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Medico;
import com.example.sistema_de_saude.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class MedicoDAO {


    private static MedicoDAO instance;
    protected EntityManager entityManager;
    public static MedicoDAO getInstance() {
        if (instance == null) {
            instance = new MedicoDAO();
        }
        return instance;
    }
    private MedicoDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Medico getById(int id) {
        return entityManager.find(Medico.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Medico> findAll() {
        return entityManager.createQuery("FROM Medico").getResultList();
    }
    public void persist(Medico medico, Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa); //add pessoa primeiro
            entityManager.persist(medico);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Medico medico) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(medico);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Medico medico) {
        try {
            entityManager.getTransaction().begin();
            medico = entityManager.find(Medico.class, medico.getId());
            entityManager.remove(medico);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(int id) {
        try {
            Medico medico = getById(id);
            remove(medico);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

