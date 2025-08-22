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

    public UsuarioSistema validarUser(String user, String senha) {
        try {
            // Validação básica
            if (user == null || user.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
                return null;
            }

            // QUERY SEGURA com parâmetros nomeados
            String jpql = "SELECT u FROM UsuarioSistema u WHERE u.login = :user AND u.senha = :senha";

            return entityManager.createQuery(jpql, UsuarioSistema.class)
                    .setParameter("user", user.trim())
                    .setParameter("senha", senha.trim())
                    .getSingleResult();

        } catch (NoResultException e) {
            return null; // Retorna null quando não encontra
        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar usuário: " + e.getMessage(), e);
        }
    }

    public UsuarioSistema trocarSenhaPorCpf(String cpf, String novaSenha) {
        try {
            // Busca pessoa pelo CPF
            PessoaDAO pessoaDAO = PessoaDAO.getInstance();
            Pessoa pessoa = pessoaDAO.findByCpf(cpf);

            if (pessoa == null) {
                return null; // CPF não encontrado
            }

            // Busca funcionário relacionado à pessoa
            FuncionarioDAO funcionarioDAO = FuncionarioDAO.getInstance();
            Funcionario funcionario = funcionarioDAO.findByPessoaId(pessoa.getId());

            if (funcionario == null) {
                return null; // Pessoa não é funcionário
            }

            // Busca usuário do sistema relacionado ao funcionário
            String jpql = "SELECT u FROM UsuarioSistema u WHERE u.funcionario.id = :funcionarioId";
            UsuarioSistema usuario = entityManager.createQuery(jpql, UsuarioSistema.class)
                    .setParameter("funcionarioId", funcionario.getId())
                    .getSingleResult();

            if (usuario == null) {
                return null; // Funcionário não tem usuário
            }

            // Atualiza a senha
            usuario.setSenha(novaSenha);
            return usuario; // Retorna o usuário para fazer merge depois

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário para troca de senha: " + e.getMessage(), e);
        }
    }

}

