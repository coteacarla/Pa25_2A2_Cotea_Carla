package com.example.lab11.controllers;

import com.example.lab11.services.CountryColoringService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/colors")
public class CountryColoringController {
    private final CountryColoringService coloringService;

    public CountryColoringController(CountryColoringService coloringService) {
        this.coloringService = coloringService;
    }

    @GetMapping
    public Map<String, Integer> getColorAssignments() {
        return coloringService.assignColors();
    }
}
