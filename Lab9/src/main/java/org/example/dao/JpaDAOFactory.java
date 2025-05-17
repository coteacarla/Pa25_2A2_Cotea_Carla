package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.EntityManagerFactorySingleton;
import org.example.repositories.*;

public class JpaDAOFactory extends DAOFactory {
    private final EntityManager em = EntityManagerFactorySingleton.getEntityManagerFactory().createEntityManager();

    @Override
    public CityRepository getCityDAO() {
        return new CityRepository(em);
    }

    @Override
    public CountryDAO getCountryDAO() {
        return new CountryRepository(em);
    }

    @Override
    public ContinentRepository getContinentDAO() {
        return new ContinentRepository(em);
    }
}

