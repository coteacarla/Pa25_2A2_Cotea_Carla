package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection con = Database.getConnection()) {
            DistanceCalculator.printDistanceBetweenCities(con, "London", "Paris");
            DistanceCalculator.printDistanceBetweenCities(con, "Tokyo", "Bucharest");

        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}