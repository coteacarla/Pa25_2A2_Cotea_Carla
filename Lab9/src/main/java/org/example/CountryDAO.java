package org.example;
import java.sql.*;

public class CountryDAO {

    public void create(String name, String code, int continent_id) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO countries (name, code, continent_id) VALUES (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setInt(3, continent_id);
            pstmt.executeUpdate();
        }
    }


    public Integer findbyname(String name) throws SQLException {
        Connection con=Database.getConnection();
        try ( Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from countries where name = '" + name + "'");
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        }
    }

    public String findbyid(Integer id) throws SQLException {
        Connection con=Database.getConnection();
        try ( Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from countries where id = '" + id + "'"))
        {
            if (rs.next()) {
                return rs.getString("name");
            }
            return null;
        }
    }
}

