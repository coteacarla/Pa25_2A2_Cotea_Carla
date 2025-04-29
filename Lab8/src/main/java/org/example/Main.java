package org.example;
import java.sql.*;

public class Main {
    public static void main(String args[]) throws SQLException {
        try {
            var continents = new ContinentDAO();
            continents.create("Europe");
            Database.getConnection().commit();
            var countries = new CountryDAO();
            int europeId = continents.findByName("Europe");
            countries.create("Romania", europeId);
            countries.create("Ukraine", europeId);
            Database.getConnection().commit();

            Connection con = Database.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select name from countries where continent_id='" + europeId + "'");
                while (rs.next()) {
                    System.out.println(rs.getString("name"));
                }

            Database.getConnection().close();

        } catch (SQLException e) {
            System.err.println(e);
            Database.getConnection().rollback();
        }
    }
}