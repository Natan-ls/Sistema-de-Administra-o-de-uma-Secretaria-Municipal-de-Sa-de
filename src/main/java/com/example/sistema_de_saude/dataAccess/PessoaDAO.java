package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

public class PessoaDAO {


    private static PessoaDAO instance;
    protected EntityManager entityManager;
    public static PessoaDAO getInstance() {
        if (instance == null) {
            instance = new PessoaDAO();
        }
        return instance;
    }
    private PessoaDAO() {
        entityManager = Persistence.createEntityManagerFactory("sistemasaudePU").createEntityManager();
    }
    public Pessoa getById(int id) {
        return entityManager.find(Pessoa.class, id);
    }
    @SuppressWarnings("unchecked")
    public List<Pessoa> findAll() {
        return entityManager.createQuery("FROM Pessoa").getResultList();
    }
    public void persist(Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void merge(Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void remove(Pessoa pessoa) {
        try {
            entityManager.getTransaction().begin();
            pessoa = entityManager.find(Pessoa.class, pessoa.getId());
            entityManager.remove(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
    public void removeById(int id) {
        try {
            Pessoa pessoa = getById(id);
            remove(pessoa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Pessoa findByCpf(String cpf) {
        try {
            String jpql = "SELECT p FROM Pessoa p WHERE p.cpf = :cpf";
            return entityManager.createQuery(jpql, Pessoa.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

