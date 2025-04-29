package org.example;
import java.sql.*;

public class CountryDAO {

    public void create(String name, int continent_id ) throws SQLException {
        Connection con=Database.getConnection();

        try( PreparedStatement pstmt = con.prepareStatement("insert into countries (name, continent_id) values (?,?)"))
        {
            pstmt.setString(1, name);
            pstmt.setInt(2, continent_id);
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

