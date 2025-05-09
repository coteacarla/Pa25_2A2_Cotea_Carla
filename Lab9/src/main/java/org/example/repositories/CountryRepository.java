package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.entities.Continent;
import org.example.entities.Country;

import java.util.List;

public class CountryRepository extends DataRepository {
    public CountryRepository(EntityManager em) {
        super(em, Country.class);
    }

    public List<Country> findByContinent(Continent conti){
        return em.createNamedQuery("Country.findByContinent", Country.class)
                .setParameter(1, conti).getResultList();
    }
}
