package org.example.Bonus;

import org.example.Database;

import java.sql.*;
import java.util.*;

public class SisterGenerator {
    private static double PROBABILITY = 0.0002;
    private static Random RAND = new Random(1);

    public static void main(String[] args) throws SQLException {
        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false);

            Statement stmt = null;
            stmt = con.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery("SELECT id FROM cities");
            List<Integer> cityIds = new ArrayList<>();

            while (rs.next()) {
                cityIds.add(rs.getInt("id"));
            }

            int n = cityIds.size();
            Set<String> existingPairs = new HashSet<>();

            PreparedStatement insertStmt = con.prepareStatement(
                    "INSERT INTO sister_cities (city1_id, city2_id) VALUES (?, ?) ON CONFLICT DO NOTHING"
            );


            for (int i = 0; i < n; i++) {
                int id1 = cityIds.get(i);
                for (int j = i + 1; j < n; j++) {
                    if (RAND.nextDouble() < PROBABILITY) {
                        int id2 = cityIds.get(j);
                        int c1 = Math.min(id1, id2);
                        int c2 = Math.max(id1, id2);
                            if (c1 != c2) {
                                String key = c1 + "_" + c2;
                                if (!existingPairs.contains(key)) {
                                    insertStmt.setInt(1, c1);
                                    insertStmt.setInt(2, c2);
                                    insertStmt.addBatch();
                                    existingPairs.add(key);

                                }
                            }
                    }
                }

                if (i % 500 == 0) {
                    insertStmt.executeBatch();
                    con.commit();
                }
            }

            insertStmt.executeBatch();
            con.commit();
            System.out.println("Sister city relationships inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
