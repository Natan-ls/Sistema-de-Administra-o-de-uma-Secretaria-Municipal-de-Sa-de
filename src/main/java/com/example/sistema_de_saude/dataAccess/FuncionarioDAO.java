package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Funcionario;
import com.example.sistema_de_saude.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class FuncionarioDAO {


    private static FuncionarioDAO instance;
    protected EntityManager entityManager;
    public static FuncionarioDAO getInstance() {
        if (instance == null) {
            instance = new FuncionarioDAO();
        }
        return instance;
    }
    private FuncionarioDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Funcionario getById(int id) {
        return entityManager.find(Funcionario.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Funcionario> findAll() {
        return entityManager.createQuery("FROM Funcionario").getResultList();
    }
    public void persist(Funcionario funcionario, Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.persist(funcionario);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Funcionario funcionario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(funcionario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Funcionario funcionario) {
        try {
            entityManager.getTransaction().begin();
            funcionario = entityManager.find(Funcionario.class, funcionario.getId());
            entityManager.remove(funcionario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }
    public void removeById(int id) {
        try {
            Funcionario funcionario = getById(id);
            remove(funcionario);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

