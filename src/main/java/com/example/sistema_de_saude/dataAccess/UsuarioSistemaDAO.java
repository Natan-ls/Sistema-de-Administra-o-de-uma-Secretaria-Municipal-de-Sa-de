package com.example.sistema_de_saude.dataAccess;

import com.example.sistema_de_saude.entity.Funcionario;
import com.example.sistema_de_saude.entity.Pessoa;
import com.example.sistema_de_saude.entity.UsuarioSistema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.List;

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
            pessoa.setFuncionario(entityManager.merge(pessoa.getFuncionario()));
            entityManager.persist(pessoa);
            entityManager.getTransaction().commit();
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
    }
    public void removeById(int id) {
        try {
            UsuarioSistema pessoa = getById(id);
            remove(pessoa);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UsuarioSistema validarUser(String login) {
        try {
            // Validação básica
            if (login == null || login.trim().isEmpty()) {
                return null;
            }

            // QUERY SEGURA com parâmetros nomeados
            String jpql = "SELECT u FROM UsuarioSistema u WHERE u.login = :login";

            return entityManager.createQuery(jpql, UsuarioSistema.class)
                    .setParameter("login", login.trim()) // <--- corrigido aqui
                    .getSingleResult();

        } catch (NoResultException e) {
            return null; // Retorna null quando não encontra
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar usuário: " + e.getMessage(), e);
        }
    }


}

