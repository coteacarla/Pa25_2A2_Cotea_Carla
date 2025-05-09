package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.entities.City;
import org.example.entities.Country;

import java.util.List;

public class CityRepository extends DataRepository {

    public CityRepository(EntityManager em) {
        super(em, City.class);
    }

    public List<City> findByCountry(Country country){
        return em.createNamedQuery("City.findByCountry", City.class)
                .setParameter(1, country).getResultList();
    }
}
