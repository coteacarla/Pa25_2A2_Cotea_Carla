package org.example.Repositories;

import jakarta.persistence.EntityManager;
import org.example.Entities.City;
import org.example.Entities.Country;

import java.util.List;

public class CityRepository extends org.example.repositories.DataRepository {

    public CityRepository(EntityManager em) {
        super(em, City.class);
    }

    public List<City> findByCountry(Country country) {
        return em.createNamedQuery("City.findByCountry", City.class)
                .setParameter(1, country).getResultList();
    }
}