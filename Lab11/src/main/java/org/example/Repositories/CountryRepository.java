package org.example.Repositories;

import org.example.Entities.Country;
import org.example.entities.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    List<Country> findByContinent(Continent continent);
}
