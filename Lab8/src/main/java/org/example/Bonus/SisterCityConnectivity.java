package org.example.Bonus;

import org.example.Database;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.example.Class.*;

public class SisterCityConnectivity {

    public static void main(String[] args) {
        try {
            Connection con = Database.getConnection();

            Map<Integer, City> cityNodes = getCityLocations();
            Map<Integer, Set<Integer>> adjacencyList = getSisterCityRelationships();

            Graph<Integer, DefaultEdge> sisterCityGraph = createGraph(adjacencyList);

            // Find maximal 2-connected sets using getBlocks()
            BiconnectivityInspector<Integer, DefaultEdge> bccInspector = new BiconnectivityInspector<>(sisterCityGraph);
            List<Set<Integer>> bccSets = new ArrayList<>();
            for (Graph<Integer, DefaultEdge> component : bccInspector.getBlocks()) {
                bccSets.add(component.vertexSet());
            }

            List<Set<Integer>> maximal2ConnectedSets = findMaximalSets(bccSets);


            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Sister City Network");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new GraphPanel(cityNodes, sisterCityGraph, maximal2ConnectedSets));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });

        } catch (SQLException e) {
            System.err.println("Error: Database error: " + e.getMessage());
        }
    }

    private static Connection getConnection() throws SQLException {
        return Database.getConnection();
    }

    private static Map<Integer, City> getCityLocations() throws SQLException {
        Map<Integer, City> cityLocations = new HashMap<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, country, capital, latitude, longitude FROM cities")) {
            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("id"));
                city.setName(rs.getString("name"));
                city.setCountry(rs.getString("country"));
                city.setCapital(rs.getBoolean("capital"));
                city.setLatitude(rs.getDouble("latitude"));
                city.setLongitude(rs.getDouble("longitude"));
                cityLocations.put(city.getId(), city);
            }
        }
        return cityLocations;
    }

    private static Map<Integer, Set<Integer>> getSisterCityRelationships() throws SQLException {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT city1_id, city2_id FROM sister_cities")) {
            while (rs.next()) {
                int city1 = rs.getInt("city1_id");
                int city2 = rs.getInt("city2_id");
                adjacencyList.computeIfAbsent(city1, k -> new HashSet<>()).add(city2);
                adjacencyList.computeIfAbsent(city2, k -> new HashSet<>()).add(city1);
            }
        }
        return adjacencyList;
    }

    private static Graph<Integer, DefaultEdge> createGraph(Map<Integer, Set<Integer>> adjacencyList) {
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        for (Integer cityId : adjacencyList.keySet()) {
            graph.addVertex(cityId);
        }
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencyList.entrySet()) {
            int city1 = entry.getKey();
            for (Integer city2 : entry.getValue()) {
                if (city1 < city2) {
                    graph.addEdge(city1, city2);
                }
            }
        }
        return graph;
    }

    private static List<Set<Integer>> findMaximalSets(List<Set<Integer>> sets) {
        List<Set<Integer>> maximalSets = new ArrayList<>();
        for (Set<Integer> set1 : sets) {
            boolean isSubSetOfAnother = false;
            for (Set<Integer> set2 : sets) {
                if (set1 != set2 && set2.containsAll(set1) && set2.size() > set1.size()) {
                    isSubSetOfAnother = true;
                    break;
                }
            }
            if (!isSubSetOfAnother) {
                maximalSets.add(set1);
            }
        }
        return maximalSets;
    }
}
