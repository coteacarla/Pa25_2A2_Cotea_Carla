package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {

    public void create(Connection con, City city) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO cities (name, country, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, city.getName());
            pstmt.setString(2, city.getCountry());
            pstmt.setBoolean(3, city.isCapital());
            pstmt.setDouble(4, city.getLatitude());
            pstmt.setDouble(5, city.getLongitude());
            pstmt.executeUpdate();
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    city.setId(keys.getInt(1));
                }
            }
        }
    }

    public City findByName(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM cities WHERE name = ?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new City(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("country"),
                            rs.getBoolean("capital"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude")
                    );
                }
            }
        }
        return null;
    }

    public City findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM cities WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new City(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("country"),
                            rs.getBoolean("capital"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude")
                    );
                }
            }
        }
        return null;
    }

    public List<City> findAll() throws SQLException {
        Connection con = Database.getConnection();
        List<City> cities = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cities")) {
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getBoolean("capital"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                ));
            }
        }
        return cities;
    }

}
