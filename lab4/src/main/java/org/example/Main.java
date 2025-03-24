package org.example;

import com.github.javafaker.Faker;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Initialize Faker for random location generation
        Faker faker = new Faker();
        Random rand = new Random();

        // 1. Generate random locations
        List<Location> locations = Arrays.asList(
                new Location(faker.name().firstName(), LocationType.FRIENDLY),
                new Location(faker.name().firstName(), LocationType.ENEMY),
                new Location(faker.name().firstName(), LocationType.FRIENDLY),
                new Location(faker.name().firstName(), LocationType.NEUTRAL),
                new Location(faker.name().firstName(), LocationType.ENEMY),
                new Location(faker.name().firstName(), LocationType.FRIENDLY)
        );

        // 2. Create routes with random weights (safety probabilities)
        List<Pair<Location, Location>> routes = new ArrayList<>();
        SimpleDirectedWeightedGraph<Location, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        locations.forEach(graph::addVertex);

        for (int i = 0; i < locations.size(); i++) {
            for (int j = 0; j < locations.size(); j++) {
                if (i != j) {
                    double time = 1 + rand.nextInt(10); // Random travel time
                    double safetyProb = rand.nextDouble(); // Random safety probability
                    routes.add(new Pair<>(locations.get(i), locations.get(j), time, safetyProb));
                    DefaultWeightedEdge edge = graph.addEdge(locations.get(i), locations.get(j));
                    graph.setEdgeWeight(edge, time);
                }
            }
        }

        // 3. Initialize MapNavigation
        MapNavigation mapNavigation = new MapNavigation(locations, routes);

        // 4. Compute fastest routes from the first location using homework method
        Location startLocation = locations.get(0);
        Map<Location, Double> fastestRoutes = mapNavigation.homework(startLocation);

        // 5. Display fastest routes
        System.out.println("Fastest Routes from " + startLocation.getName() + ":");
        fastestRoutes.forEach((location, time) ->
                System.out.println(location.getName() + ": " + time + " min"));

        // 6. Compute safest routes using Floyd-Warshall algorithm
        Map<Location, Map<Location, Double>> safestRoutes = mapNavigation.computeSafestRoutes();

        // 7. Display safest routes
        System.out.println("\nSafest Routes (Maximized Probability) from " + startLocation.getName() + ":");
        safestRoutes.get(startLocation).forEach((location, prob) ->
                System.out.println(location.getName() + ": " + prob));

        // 8. Count locations by type along the safest routes
        Map<Location, Map<LocationType, Long>> typeCounts = mapNavigation.countLocationTypesAlongRoutes(safestRoutes);

        // 9. Display the count of location types along the safest routes
        System.out.println("\nCount of Location Types along Safest Routes:");
        typeCounts.forEach((start, typeCount) -> {
            System.out.println("\nFrom " + start.getName() + ":");
            typeCount.forEach((type, count) ->
                    System.out.println("Type: " + type + " -> " + count + " occurrences"));
        });
    }
}
