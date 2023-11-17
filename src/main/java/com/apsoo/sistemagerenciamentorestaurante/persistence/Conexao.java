package com.apsoo.sistemagerenciamentorestaurante.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    final private static String host = "localhost";
    final private static String database = "apsoo_restaurante";
    final private static String username = "root";
    final private static String password =  "";

    public static Connection abreConexao() {
        String urlConexao = "jdbc:mysql://" + host + "/" + database;

        try {
            return DriverManager.getConnection(urlConexao, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void fechaConexao(Connection conexao) {
        try {
            if(conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
