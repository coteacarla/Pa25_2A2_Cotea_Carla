package org.example;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.*;
import java.util.stream.Collectors;

public class MapNavigation {
    private SimpleDirectedWeightedGraph<Location, DefaultWeightedEdge> graph;
    private List<Location> locations;
    private List<Pair<Location, Location>> paths;

    public MapNavigation(List<Location> locations, List<Pair<Location, Location>> paths) {
        this.locations = locations;
        this.paths = paths;
        graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        // Add locations to the graph
        locations.forEach(graph::addVertex);

        // Add paths with safety probabilities (as edge weights)
        for (Pair<Location, Location> pair : paths) {
            DefaultWeightedEdge edge = graph.addEdge(pair.getFrom(), pair.getTo());
            graph.setEdgeWeight(edge, pair.getSafetyProbability());
        }
    }

    // Original homework function (Fastest Routes using Dijkstra)
    public Map<Location, Double> homework(Location start) {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        return locations.stream()
                .filter(loc -> !loc.equals(start))
                .collect(Collectors.toMap(
                        loc -> loc,
                        loc -> dijkstra.getPathWeight(start, loc)
                ));
    }

    // Compute the safest paths for all pairs using Floyd-Warshall
    public Map<Location, Map<Location, Double>> computeSafestRoutes() {
        int n = locations.size();
        double[][] maxProb = new double[n][n];

        // Initialize the maxProb array with probabilities
        for (int i = 0; i < n; i++) {
            Arrays.fill(maxProb[i], 0.0);  // Initialize all as 0 (no path yet)
            maxProb[i][i] = 1.0;  // The probability of a location to itself is 1
        }

        // Fill the maxProb array with the probabilities from the graph
        for (Pair<Location, Location> pair : paths) {
            int fromIndex = locations.indexOf(pair.getFrom());
            int toIndex = locations.indexOf(pair.getTo());
            maxProb[fromIndex][toIndex] = pair.getSafetyProbability();
        }

        // Floyd-Warshall algorithm for all pairs of safest paths (maximizing product of probabilities)
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    maxProb[i][j] = Math.max(maxProb[i][j], maxProb[i][k] * maxProb[k][j]);
                }
            }
        }

        // Map the results back to a more user-friendly structure
        Map<Location, Map<Location, Double>> safestRoutes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Location start = locations.get(i);
            Map<Location, Double> safestFromStart = new HashMap<>();
            for (int j = 0; j < n; j++) {
                Location end = locations.get(j);
                safestFromStart.put(end, maxProb[i][j]);
            }
            safestRoutes.put(start, safestFromStart);
        }

        return safestRoutes;
    }

    // Group locations by type and count types along the safest routes
    public Map<Location, Map<LocationType, Long>> countLocationTypesAlongRoutes(Map<Location, Map<Location, Double>> safestRoutes) {
        Map<Location, Map<LocationType, Long>> typeCounts = new HashMap<>();

        safestRoutes.forEach((start, endMap) -> {
            endMap.forEach((end, probability) -> {
                if (probability > 0) {  // Only consider paths with non-zero probability
                    List<Location> route = findSafestRoute(start, end); // Find the safest route (if needed)
                    Map<LocationType, Long> counts = route.stream()
                            .collect(Collectors.groupingBy(Location::getType, Collectors.counting()));
                    typeCounts.put(start, counts);
                }
            });
        });

        return typeCounts;
    }

    // Find the safest route for a pair of locations (Optional for more detail)
    public List<Location> findSafestRoute(Location start, Location end) {
        // Implement logic to backtrack and construct the safest path between `start` and `end`
        // For now, return a dummy route (you would implement path reconstruction here)
        return Arrays.asList(start, end);
    }
}
