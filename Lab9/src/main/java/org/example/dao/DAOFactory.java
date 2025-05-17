package org.example.dao;

import org.example.dao.JpaDAOFactory;
import org.example.repositories.*;
import org.example.*;

public abstract class DAOFactory {
    ///public abstract CityDAO getCityDAO();
    public abstract CountryDAO getCountryDAO();
    public abstract ContinentDAO getContinentDAO();

    public static DAOFactory getFactory(String type) {
        return switch (type.toLowerCase()) {
            case "jpa" -> new JpaDAOFactory();
            case "jdbc" -> new JdbcDAOFactory();
            default -> throw new IllegalArgumentException("Unknown DAO type: " + type);
        };
    }
}
