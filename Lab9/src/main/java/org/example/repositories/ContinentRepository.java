package org.example.repositories;
import org.example.entities.Continent;
import jakarta.persistence.*;

import java.util.List;

public class ContinentRepository {
    private EntityManager em;

    public ContinentRepository(EntityManager em) {
        this.em = em;
    }

    public void create(Continent continent) {
        em.getTransaction().begin();
        em.persist(continent);
        em.getTransaction().commit();
    }

    public Continent findById(Integer id) {
        return em.find(Continent.class, id);
    }

    public List<Continent> findByName(String name) {
        return em.createQuery("SELECT c FROM Continent c WHERE c.name LIKE :name", Continent.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
}
