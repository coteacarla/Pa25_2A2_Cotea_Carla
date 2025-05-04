package org.example;

import java.sql.*;

public class Main {
    public static void main(String args[]) {
        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);

            Continent europe = new Continent(0, "Europe");
            ContinentDAO continentDAO = new ContinentDAO();
            continentDAO.create(europe);
            con.commit();


        } catch (SQLException e) {
            System.err.println("Database operation failed: " + e.getMessage());
            try {
                Database.getConnection().rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
        }
    }
}
