package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Continent;
import org.example.repositories.ContinentRepository;
import org.example.logger.LoggerConfig;

import java.util.logging.Logger;

public class Main {
    private static final Logger logger = LoggerConfig.getLogger();

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = EntityManagerFactorySingleton.getEntityManagerFactory();
            em = emf.createEntityManager();

            ContinentRepository continentRepo = new ContinentRepository(em);

            logger.info("Creating new continent: Asia");
            Continent continent = new Continent("Asia");
            continentRepo.persist(continent);

            logger.info("Fetching continent by ID: " + continent.getId());
            Continent foundContinent = (Continent) continentRepo.findById(continent.getId());
            System.out.println("Found Continent: " + foundContinent);

            logger.info("Searching continents with 'A' in their name...");
            continentRepo.findByName("A").forEach(System.out::println);

        } catch (Exception e) {
            logger.severe("Error in main: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
        }
    }
}
