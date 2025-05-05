package org.example;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class CityImporter {

    public void importFromCSV() throws IOException, SQLException {
        CityDAO cityDAO = new CityDAO();
        try (Scanner scanner = new Scanner(new File("C:/Users/Carla/Desktop/Uni/Pa25_2A2_Cotea_Carla/Lab8/src/main/resources/concap.csv"))) {
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(",");
                String country = tokens[0].replaceAll("\"", "");
                String name = tokens[1].replaceAll("\"", "");
                double lat = Double.parseDouble(tokens[2]);
                double lon = Double.parseDouble(tokens[3]);

                City city = new City();
                city.setName(name);
                city.setCountry(country);
                city.setCapital(true);
                city.setLatitude((double) (lat * 1_000_000));
                city.setLongitude((double) (lon * 1_000_000));
                cityDAO.create(city);
            }
        }
    }
}
