package com.example.lab11.repositories;

import com.example.lab11.entities.Continent;
import com.example.lab11.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findByContinent(Continent continent);
}

