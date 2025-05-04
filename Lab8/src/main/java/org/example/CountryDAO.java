package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    public void create(Connection con, Country country) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, country.getName());
            pstmt.setInt(2, country.getCode());
            pstmt.setInt(3, country.getContinent_id());
            pstmt.executeUpdate();
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    country.setId(keys.getInt(1));
                }
            }
        }
    }

    public Country findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM countries WHERE name = ?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Country(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("code"),
                            rs.getInt("continent_id")
                    );
                }
            }
        }
        return null;
    }

    public Country findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM countries WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Country(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("code"),
                            rs.getInt("continent_id")
                    );
                }
            }
        }
        return null;
    }

    public List<Country> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<Country> countries = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM countries")) {
            while (rs.next()) {
                countries.add( new Country(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("code"),
                        rs.getInt("continent_id")
                ));
            }
        }
        return countries;
    }


}
