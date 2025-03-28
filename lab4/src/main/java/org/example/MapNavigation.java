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

        locations.forEach(graph::addVertex);
        for (Pair<Location, Location> pair : paths) {
            DefaultWeightedEdge edge = graph.addEdge(pair.getFrom(), pair.getTo());
            graph.setEdgeWeight(edge, pair.getSafetyProbability());
        }
    }


    public Map<Location, Double> homework(Location start) {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        return locations.stream()
                .filter(loc -> !loc.equals(start))
                .collect(Collectors.toMap(
                        loc -> loc,
                        loc -> dijkstra.getPathWeight(start, loc)
                ));
    }


    public Map<Location, Map<Location, Double>> computeSafestRoutes() {
        int n = locations.size();
        double[][] maxProb = new double[n][n];


        for (int i = 0; i < n; i++) {
            Arrays.fill(maxProb[i], 0.0);
            maxProb[i][i] = 1.0;
        }


        for (Pair<Location, Location> pair : paths) {
            int fromIndex = locations.indexOf(pair.getFrom());
            int toIndex = locations.indexOf(pair.getTo());
            maxProb[fromIndex][toIndex] = pair.getSafetyProbability();
        }


        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    maxProb[i][j] = Math.max(maxProb[i][j], maxProb[i][k] * maxProb[k][j]);
                }
            }
        }


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

    public Map<Location, Map<LocationType, Long>> countLocationTypesAlongRoutes(Map<Location, Map<Location, Double>> safestRoutes) {
        Map<Location, Map<LocationType, Long>> typeCounts = new HashMap<>();

        safestRoutes.forEach((start, endMap) -> {
            endMap.forEach((end, probability) -> {
                if (probability > 0) {
                    List<Location> route = findSafestRoute(start, end);
                    Map<LocationType, Long> counts = route.stream()
                            .collect(Collectors.groupingBy(Location::getType, Collectors.counting()));
                    typeCounts.put(start, counts);
                }
            });
        });

        return typeCounts;
    }


    public List<Location> findSafestRoute(Location start, Location end) {
        return Arrays.asList(start, end);
    }
}
