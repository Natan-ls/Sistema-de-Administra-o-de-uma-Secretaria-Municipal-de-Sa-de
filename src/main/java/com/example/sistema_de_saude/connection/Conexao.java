package com.example.sistema_de_saude.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/dbsistemasaude";
    private static final String USER = "admin";
    private static final String SENHA = "admin";

    public static Connection obterConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, SENHA);
        } catch (SQLException | ClassNotFoundException e) {
            throw new SQLException("Erro ao acessar o banco: " + e.getMessage());
        }
    }

    public static void fecharConexao(Connection connection) throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

}
