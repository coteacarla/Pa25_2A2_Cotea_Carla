package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL ="jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "STUDENT";
    private static Connection connection = null;

    private Database() {
    }

    public static Connection getConnection() {
       if(connection == null) {
           createConnection();
       }
        return connection;
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeConnection() throws SQLException {
        connection.close();
        connection = null;
    }
}