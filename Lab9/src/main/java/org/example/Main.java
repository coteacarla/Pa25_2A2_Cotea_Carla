package org.example;

import org.example.entities.Continent;
import org.example.repositories.ContinentRepository;
import jakarta.persistence.*;



public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExamplePU");
        EntityManager em = emf.createEntityManager();

        ContinentRepository continentRepo = new ContinentRepository(em);

        Continent continent = new Continent("Asia");
        continentRepo.create(continent);

        Continent foundContinent = continentRepo.findById(continent.getId());
        System.out.println("Found Continent: " + foundContinent);

        System.out.println("Searching continents with 'A' in their name...");
        continentRepo.findByName("A").forEach(System.out::println);

        em.close();
        emf.close();
    }
}
