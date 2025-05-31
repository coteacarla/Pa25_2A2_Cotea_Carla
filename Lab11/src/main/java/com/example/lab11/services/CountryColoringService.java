package com.example.lab11.services;

import com.example.lab11.entities.Country;
import com.example.lab11.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryColoringService {
    private final CountryRepository countryRepository;

    public CountryColoringService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Map<String, Integer> assignColors() {
        List<Country> countries = countryRepository.findAll();

        // Sort countries by number of total neighbors (including inverse)
        countries.sort((a, b) -> Integer.compare(
                b.getAllNeighbors().size(),
                a.getAllNeighbors().size()
        ));

        Map<Country, Integer> colorMap = new HashMap<>();

        for (Country country : countries) {
            Set<Integer> usedColors = new HashSet<>();

            // Use both direct and inverse neighbors
            for (Country neighbor : country.getAllNeighbors()) {
                if (colorMap.containsKey(neighbor)) {
                    usedColors.add(colorMap.get(neighbor));
                }
            }

            // Assign the lowest unused color
            int color = 1;
            while (usedColors.contains(color)) {
                color++;
            }
            colorMap.put(country, color);
        }


        Map<String, Integer> result = new LinkedHashMap<>();
        for (Country country : countries) {
            result.put(country.getName(), colorMap.get(country));
        }

        return result;
    }
}
