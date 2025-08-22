package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Paciente;
import com.example.sistema_de_saude.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

public class PacienteDAO {


    private static PacienteDAO instance;
    protected EntityManager entityManager;
    public static PacienteDAO getInstance() {
        if (instance == null) {
            instance = new PacienteDAO();
        }
        return instance;
    }
    private PacienteDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Paciente getById(Long id) {
        return entityManager.find(Paciente.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Paciente> findAll() {
        return entityManager.createQuery("FROM Paciente").getResultList();
    }
    public void persist(Paciente paciente, Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.persist(paciente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Paciente paciente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(paciente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(Paciente paciente) {
        try {
            entityManager.getTransaction().begin();
            paciente = entityManager.find(Paciente.class, paciente.getId());
            entityManager.remove(paciente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(Long id) {
        try {
            Paciente paciente = getById(id);
            remove(paciente);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Paciente findBySus(String numeroSus) {
        try {
            String jpql = "SELECT p FROM Paciente p WHERE p.numeroSus = :numeroSus";
            return entityManager.createQuery(jpql, Paciente.class)
                    .setParameter("numeroSus", numeroSus)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

