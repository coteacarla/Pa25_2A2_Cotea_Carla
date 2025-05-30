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

        countries.sort((a, b) -> Integer.compare(b.getNeighbors().size(), a.getNeighbors().size()));

        Map<Country, Integer> colorMap = new HashMap<>();

        for (Country country : countries) {
            Set<Integer> usedColors = new HashSet<>();
            for (Country neighbor : country.getNeighbors()) {
                if (colorMap.containsKey(neighbor)) {
                    usedColors.add(colorMap.get(neighbor));
                }
            }
            int color = 1;
            while (usedColors.contains(color)) {
                color++;
            }
            colorMap.put(country, color);
        }

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Country, Integer> entry : colorMap.entrySet()) {
            result.put(entry.getKey().getName(), entry.getValue());
        }

        return result;
    }
}
