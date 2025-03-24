package org.example;

import com.github.javafaker.Faker;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Faker faker = new Faker();

        // Generate random locations using Faker
        List<Location> locations = Arrays.asList(
                new Location(faker.name().firstName(), LocationType.FRIENDLY),
                new Location(faker.name().firstName(), LocationType.ENEMY),
                new Location(faker.name().firstName(), LocationType.FRIENDLY),
                new Location(faker.name().firstName(), LocationType.NEUTRAL),
                new Location(faker.name().firstName(), LocationType.ENEMY),
                new Location(faker.name().firstName(), LocationType.FRIENDLY)
        );

        // 1. Friendly locations in a TreeSet (sorted naturally by name)
        TreeSet<Location> friendlyLocations = locations.stream()
                .filter(loc -> loc.getType() == LocationType.FRIENDLY)
                .collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Friendly Locations (Sorted by Name):");
        friendlyLocations.forEach(System.out::println);

        // 2. Enemy locations in a LinkedList, sorted by type and then by name
        LinkedList<Location> enemyLocations = locations.stream()
                .filter(loc -> loc.getType() == LocationType.ENEMY)
                .sorted(Comparator.comparing(Location::getType).thenComparing(Location::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("\nEnemy Locations (Sorted by Type, then Name):");
        enemyLocations.forEach(System.out::println);

        // 2. Neutral locations in a LinkedList
        LinkedList<Location> neutralLocations = locations.stream()
                .filter(loc -> loc.getType() == LocationType.NEUTRAL)
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("\nNeutral Locations :");
        neutralLocations.forEach(System.out::println);



        // 3. Set up the graph using JGraphT (SimpleDirectedGraph)
        SimpleDirectedGraph<Location, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);

        // Add locations as vertices to the graph
        locations.forEach(graph::addVertex);

        // 4. Define some random routes (edges with time and safety probability)
        Random rand = new Random();
        List<Pair<Location, Location>> routes = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            for (int j = i + 1; j < locations.size(); j++) {
                // Random time and safety probability between locations
                double time = rand.nextInt(10) + 1;  // Random time between 1 and 10
                double safetyProb = rand.nextDouble();  // Random safety probability between 0 and 1
                routes.add(new Pair<>(locations.get(i), locations.get(j), time, safetyProb));
                graph.addEdge(locations.get(i), locations.get(j)); // Create a directed edge
            }
        }

        // 5. Calculate the safest route and fastest route using modified Dijkstra's Algorithm
        Location startLocation = locations.get(0);  // Assume the first location is the start

        MapNavigation mapNavigation = new MapNavigation(locations, routes);
        Map<Location, Double> safetyProbabilities = new HashMap<>();
        Map<Location, Double> fastestTimes = new HashMap<>();

        // Find the safest and fastest path from the start location to all other locations
        for (Location loc : locations) {
            // Find safest route
            List<Location> safestRoute = mapNavigation.findSafestRoute(startLocation, loc);
            double safetyProbability = 1.0;
            for (int i = 0; i < safestRoute.size() - 1; i++) {
                Location from = safestRoute.get(i);
                Location to = safestRoute.get(i + 1);
                Pair<Location, Location> route = routes.stream()
                        .filter(r -> r.getFrom().equals(from) && r.getTo().equals(to))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Route not found"));

                safetyProbability *= route.getSafetyProbability();
            }
            safetyProbabilities.put(loc, safetyProbability);

            // Find fastest route (sum of times)
            List<Location> fastestRoute = mapNavigation.findFastestRoute(startLocation, loc);
            double fastestTime = 0.0;
            for (int i = 0; i < fastestRoute.size() - 1; i++) {
                Location from = fastestRoute.get(i);
                Location to = fastestRoute.get(i + 1);
                Pair<Location, Location> route = routes.stream()
                        .filter(r -> r.getFrom().equals(from) && r.getTo().equals(to))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Route not found"));

                fastestTime += route.getTime();
            }
            fastestTimes.put(loc, fastestTime);
        }

        // 6. Group locations by type and display fastest times
        Map<LocationType, List<Location>> locationsByType = new HashMap<>();
        for (Location location : locations) {
            locationsByType.computeIfAbsent(location.getType(), k -> new ArrayList<>()).add(location);
        }

        // Sort locations by type and then by name
        locationsByType.forEach((type, locs) -> {
            locs.sort(Comparator.naturalOrder());  // Sort by name

            System.out.println("\n" + type + " Locations (Safest & Fastest Routes):");
            locs.forEach(loc -> {
                Double safetyProb = safetyProbabilities.get(loc);
                Double fastestTime = fastestTimes.get(loc);
                System.out.println(loc + ": " +
                        (safetyProb == 0.0 ? "No Path" : safetyProb + " safety probability") +
                        ", Fastest Time: " + (fastestTime == 0.0 ? "No Path" : fastestTime + " time"));
            });
        });
    }
}
