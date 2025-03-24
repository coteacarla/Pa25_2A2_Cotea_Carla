package org.example;

import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import com.github.javafaker.Faker;

import java.util.*;

public class MapNavigation {

    private SimpleDirectedGraph<Location, DefaultEdge> graph;
    private List<Location> locations;
    private List<Pair<Location, Location>> routes;

    public MapNavigation(List<Location> locations, List<Pair<Location, Location>> routes) {
        this.locations = locations;
        this.routes = routes;

        // Initialize the graph
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        locations.forEach(graph::addVertex);

        // Add edges to the graph
        for (Pair<Location, Location> pair : routes) {
            graph.addEdge(pair.getFrom(), pair.getTo());
        }
    }

    // Find the safest route between two locations
    public List<Location> findSafestRoute(Location start, Location end) {
        Map<Location, Double> safetyProbabilities = new HashMap<>();
        safetyProbabilities.put(start, 1.0);  // Start with 100% safety probability

        PriorityQueue<Location> pq = new PriorityQueue<>(Comparator.comparing(safetyProbabilities::get).reversed());
        pq.add(start);

        Map<Location, Location> previousLocations = new HashMap<>();
        previousLocations.put(start, null);

        while (!pq.isEmpty()) {
            Location current = pq.poll();

            if (current.equals(end)) {
                break;  // Reached destination, exit
            }

            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                Location neighbor = graph.getEdgeTarget(edge);
                Pair<Location, Location> route = getRoute(current, neighbor);

                double newSafetyProbability = safetyProbabilities.get(current) * route.getSafetyProbability();
                if (newSafetyProbability > safetyProbabilities.getOrDefault(neighbor, 0.0)) {
                    safetyProbabilities.put(neighbor, newSafetyProbability);
                    pq.add(neighbor);
                    previousLocations.put(neighbor, current);
                }
            }
        }

        // Reconstruct the safest path
        List<Location> path = new ArrayList<>();
        for (Location at = end; at != null; at = previousLocations.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    // Find the fastest route between two locations
    public List<Location> findFastestRoute(Location start, Location end) {
        Map<Location, Double> travelTimes = new HashMap<>();
        travelTimes.put(start, 0.0);  // Start with 0 time

        PriorityQueue<Location> pq = new PriorityQueue<>(Comparator.comparing(travelTimes::get));
        pq.add(start);

        Map<Location, Location> previousLocations = new HashMap<>();
        previousLocations.put(start, null);

        while (!pq.isEmpty()) {
            Location current = pq.poll();

            if (current.equals(end)) {
                break;  // Reached destination, exit
            }

            for (DefaultEdge edge : graph.outgoingEdgesOf(current)) {
                Location neighbor = graph.getEdgeTarget(edge);
                Pair<Location, Location> route = getRoute(current, neighbor);

                double newTravelTime = travelTimes.get(current) + route.getTime();
                if (newTravelTime < travelTimes.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    travelTimes.put(neighbor, newTravelTime);
                    pq.add(neighbor);
                    previousLocations.put(neighbor, current);
                }
            }
        }

        // Reconstruct the fastest path
        List<Location> path = new ArrayList<>();
        for (Location at = end; at != null; at = previousLocations.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    // Helper method to find the route (Pair) between two locations
    private Pair<Location, Location> getRoute(Location from, Location to) {
        return routes.stream()
                .filter(route -> route.getFrom().equals(from) && route.getTo().equals(to))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No route found between " + from + " and " + to));
    }

    // Store and calculate statistics for multiple routes
    public Map<LocationType, Integer> calculateLocationTypesInRoute(List<Location> path) {
        Map<LocationType, Integer> locationTypeCount = new HashMap<>();
        for (Location loc : path) {
            locationTypeCount.put(loc.getType(), locationTypeCount.getOrDefault(loc.getType(), 0) + 1);
        }
        return locationTypeCount;
    }

    // Generate random test problems
    public static MapNavigation generateRandomProblem(int numLocations, int numRoutes) {
        List<Location> locations = new ArrayList<>();
        List<Pair<Location, Location>> routes = new ArrayList<>();
        Random rand = new Random();
        Faker faker = new Faker();

        // Generate locations with random types
        for (int i = 0; i < numLocations; i++) {
            LocationType type = LocationType.values()[rand.nextInt(LocationType.values().length)];
            locations.add(new Location(faker.name().firstName(), type));
        }

        // Generate random routes between locations with random time and safety probabilities
        for (int i = 0; i < numRoutes; i++) {
            Location from = locations.get(rand.nextInt(numLocations));
            Location to = locations.get(rand.nextInt(numLocations));
            double time = rand.nextInt(10) + 1;  // Random travel time between 1 and 10
            double safetyProb = rand.nextDouble();  // Random safety probability between 0 and 1
            routes.add(new Pair<>(from, to, time, safetyProb));
        }

        return new MapNavigation(locations, routes);
    }

    // Method to run the problem and compute statistics
    public static void runTest(MapNavigation mapNavigation) {
        List<Location> allLocations = mapNavigation.locations;
        Map<LocationType, List<Location>> pathsByType = new HashMap<>();

        // Process each location pair and find the safest route and fastest route
        for (Location start : allLocations) {
            for (Location end : allLocations) {
                if (!start.equals(end)) {
                    // Safest route
                    List<Location> safestRoute = mapNavigation.findSafestRoute(start, end);
                    Map<LocationType, Integer> safestTypeCount = mapNavigation.calculateLocationTypesInRoute(safestRoute);

                    // Store or process the results for safest route
                    safestTypeCount.forEach((type, count) -> {
                        pathsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(safestRoute.get(0));  // Store path details
                    });

                    // Fastest route
                    List<Location> fastestRoute = mapNavigation.findFastestRoute(start, end);
                    Map<LocationType, Integer> fastestTypeCount = mapNavigation.calculateLocationTypesInRoute(fastestRoute);

                    // Store or process the results for fastest route
                    fastestTypeCount.forEach((type, count) -> {
                        pathsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(fastestRoute.get(0));  // Store path details
                    });
                }
            }
        }

        // Example statistics: Print the number of locations by type in the routes
        pathsByType.forEach((type, paths) -> {
            long count = paths.size();
            System.out.println(type + " Locations in Routes: " + count);
        });
    }

    public static void main(String[] args) {
        MapNavigation mapNavigation = generateRandomProblem(1000, 5000);
        runTest(mapNavigation);
    }
}
