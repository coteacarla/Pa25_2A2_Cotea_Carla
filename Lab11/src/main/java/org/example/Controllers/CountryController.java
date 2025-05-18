package org.example.Controllers;

import org.example.Entities.Country;
import org.example.Repositories.CountryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryRepository countryRepository;

    public CountryController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public List<org.example.Entities.Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
