package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static final String URL ="jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "STUDENT";
    private static HikariDataSource dataSource;

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            createDataSource();
        }
        return dataSource.getConnection();
    }

    private static void createDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setAutoCommit(false);

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize HikariCP connection pool", e);
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void shutdownDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}