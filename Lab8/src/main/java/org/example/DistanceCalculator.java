package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class DistanceCalculator {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public static double calculateDistance(City city1, City city2) {
        double lat1Rad = Math.toRadians(city1.getLatitude());
        double lon1Rad = Math.toRadians(city1.getLongitude());
        double lat2Rad = Math.toRadians(city2.getLatitude());
        double lon2Rad = Math.toRadians(city2.getLongitude());

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    public static void printDistanceBetweenCities(Connection con, String city1Name, String city2Name) {
        CityDAO cityDAO = new CityDAO();

        try {
            City city1 = cityDAO.findByName(city1Name);
            City city2 = cityDAO.findByName(city2Name);

            if (city1 != null && city2 != null) {
                double distance = calculateDistance(city1, city2);
                System.out.printf("The distance between %s and %s is approximately %.2f km.\n",
                        city1.getName(), city2.getName(), distance);
            } else {
                System.out.println("Could not find one or both of the specified cities.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving city data: " + e.getMessage());
            e.printStackTrace();
        }
    }


}