package org.example.repositories;

import org.example.entities.Continent;
import jakarta.persistence.*;

import java.util.List;

public class ContinentRepository extends org.example.repositories.DataRepository {

    public ContinentRepository(EntityManager em) {
        super(em, Continent.class);
    }

    public List<Continent> findByName(String name) {
        return em.createQuery("SELECT c FROM Continent c WHERE c.name LIKE :name", Continent.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}