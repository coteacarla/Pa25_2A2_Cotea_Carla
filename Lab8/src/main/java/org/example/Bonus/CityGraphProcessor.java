package org.example.Bonus;

import org.example.Database;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;

import java.sql.*;
import java.util.*;

public class CityGraphProcessor {


    public Graph<Integer, DefaultEdge> createGraphFromDatabase(Connection con) throws SQLException {
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);


        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT city1_id, city2_id FROM sister_cities");


        while (rs.next()) {
            int city1 = rs.getInt("city1_id");
            int city2 = rs.getInt("city2_id");


            graph.addVertex(city1);
            graph.addVertex(city2);


            graph.addEdge(city1, city2);
        }

        return graph;
    }


    public List<Set<Integer>> getMaximal2ConnectedSubgraphs(Graph<Integer, DefaultEdge> graph) {
        List<Set<Integer>> connectedComponents = new ArrayList<>();


        BiconnectivityInspector<Integer, DefaultEdge> biconnectivityInspector = new BiconnectivityInspector<>(graph);


        Set<Graph<Integer, DefaultEdge>> blocks = biconnectivityInspector.getBlocks();


        for (Graph<Integer, DefaultEdge> block : blocks) {
            Set<Integer> component = block.vertexSet();
            connectedComponents.add(component);
        }

        return connectedComponents;
    }


    public static void main(String[] args) {
        CityGraphProcessor processor = new CityGraphProcessor();

        try (Connection con = Database.getConnection()) {

            Graph<Integer, DefaultEdge> graph = processor.createGraphFromDatabase(con);


            List<Set<Integer>> maximal2ConnectedSubgraphs = processor.getMaximal2ConnectedSubgraphs(graph);


            System.out.println("Maximal 2-connected subgraphs:");
            for (Set<Integer> component : maximal2ConnectedSubgraphs) {
                System.out.println(component);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
