package com.example.sistema_de_saude.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class Conexao {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String HOST = dotenv.get("POSTGRES_HOST");
    private static final String USER = dotenv.get("POSTGRES_USER");
    private static final String SENHA = dotenv.get("POSTGRES_PASSWORD");
    private static final String DB = dotenv.get("POSTGRES_DB");
    private static final String PORT = dotenv.get("POSTGRES_PORT");

    private static final String URL = "jdbc:postgresql://"+ HOST +":"+ PORT + "/"+ DB;


    public static Connection obterConexao() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, SENHA);
        } catch (SQLException e) {
            throw new SQLException("Erro ao acessar o banco: " + e.getMessage());
        }
    }

    public static void fecharConexao(Connection connection) throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }

}
