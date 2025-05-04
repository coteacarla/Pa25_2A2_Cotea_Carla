package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContinentDAO {


    public void create(Connection con, Continent continent) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO continents ( name ) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, continent.getName());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    continent.setId(generatedKeys.getInt(1));
                    System.out.println("Inserted continent with ID: " + continent.getId());
                }
            }
        }
    }

    public Continent findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT id, name FROM continents WHERE name = ?")) {
            stmt.setString(1, name);
            con.commit();
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Continent(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    public Continent findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement(
                "SELECT id, name FROM continents WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Continent(rs.getInt("id"), rs.getString("name"));
                }
            }
        }
        return null;
    }

    public List<Continent> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<Continent> continents = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM continents")) {
            while (rs.next()) {
                continents.add(new Continent(rs.getInt("id"), rs.getString("name")));
            }
        }
        return continents;
    }
}
