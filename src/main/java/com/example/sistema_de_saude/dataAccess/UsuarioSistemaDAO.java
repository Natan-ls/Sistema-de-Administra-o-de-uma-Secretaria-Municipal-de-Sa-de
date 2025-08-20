package com.example.sistema_de_saude.dataAccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class UsuarioSistemaDAO {


    private static UsuarioSistemaDAO instance;
    protected EntityManager entityManager;
    public static UsuarioSistemaDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioSistemaDAO();
        }
        return instance;
    }
    private UsuarioSistemaDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public UsuarioSistema getById(int id) {
        return entityManager.find(UsuarioSistema.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<UsuarioSistema> findAll() {
        return entityManager.createQuery("FROM UsuarioSistema").getResultList();
    }
    public void persist(UsuarioSistema pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(UsuarioSistema pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(UsuarioSistema pessoa) {
        try {
            entityManager.getTransaction().begin();
            pessoa = entityManager.find(UsuarioSistema.class, pessoa.getId());
            entityManager.remove(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            UsuarioSistema pessoa = getById(id);
            remove(pessoa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

