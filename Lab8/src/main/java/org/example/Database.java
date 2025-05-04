package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static final String URL ="jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "Student";
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

        // You can configure other pool settings here, such as:
        // config.setMaximumPoolSize(10); // Maximum number of connections in the pool
        // config.setMinimumIdle(5);    // Minimum number of idle connections
        // config.setMaxLifetime(1800000); // Maximum lifetime of a connection (milliseconds)
        // config.setConnectionTimeout(5000); // Maximum time to wait for a connection (milliseconds)
        // config.setIdleTimeout(600000);    // Maximum time a connection can sit idle (milliseconds)
        config.setAutoCommit(false); // Set default auto-commit behavior

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize HikariCP connection pool", e);
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close(); // Return the connection to the pool
        }
    }

    public static void shutdownDataSource() {
        if (dataSource != null) {
            dataSource.close(); // Close all connections in the pool
        }
    }
}