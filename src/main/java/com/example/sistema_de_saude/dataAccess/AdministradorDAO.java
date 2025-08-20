package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Administrador;
import com.example.sistema_de_saude.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class AdministradorDAO {


    private static AdministradorDAO instance;
    protected EntityManager entityManager;
    public static AdministradorDAO getInstance() {
        if (instance == null) {
            instance = new AdministradorDAO();
        }
        return instance;
    }
    private AdministradorDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Administrador getById(int id) {
        return entityManager.find(Administrador.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Administrador> findAll() {
        return entityManager.createQuery("FROM Administrador").getResultList();
    }
    public void persist(Administrador administrador, Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.persist(administrador);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Administrador administrador) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(administrador);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Administrador administrador) {
        try {
            entityManager.getTransaction().begin();
            administrador = entityManager.find(Administrador.class, administrador.getId());
            entityManager.remove(administrador);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            Administrador administrador = getById(id);
            remove(administrador);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

