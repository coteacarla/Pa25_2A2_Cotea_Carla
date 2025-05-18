package org.example.dao;

import org.example.*;
import org.example.repositories.CountryRepository;

public class JdbcDAOFactory extends DAOFactory {

//    @Override
//    public CountryDAO getCountryDAO() {
//        return new CountryDAO();
//    }

    @Override
    public ContinentDAO getContinentDAO() {
        return new ContinentDAO();
    }

    @Override
    public CityDAO getCityDAO() {
        return new CityDAO();
    }
}