package org.example;

import org.example.entities.Continent;
import org.example.repositories.ContinentRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExamplePU");
        EntityManager em = emf.createEntityManager();

        ContinentRepository continentRepo = new ContinentRepository(em);

        // Create a new Continent
        Continent continent = new Continent("Asia");
        continentRepo.create(continent);

        // Find Continent by ID
        Continent foundContinent = continentRepo.findById(continent.getId());
        System.out.println("Found Continent: " + foundContinent);

        // Find continents by name pattern
        System.out.println("Searching continents with 'A' in their name...");
        continentRepo.findByName("A").forEach(System.out::println);

        em.close();
        emf.close();
    }
}
