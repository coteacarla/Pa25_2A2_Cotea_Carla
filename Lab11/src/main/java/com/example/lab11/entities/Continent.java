package com.example.lab11.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "continents")
@NamedQueries({
        @NamedQuery(name = "Continent.findAll",
                query = "SELECT e FROM Continent e WHERE e.name LIKE ?1 ORDER BY e.name")

})
public class Continent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "continent", fetch = FetchType.LAZY)
    private List<Country> countries;

    // Constructors, Getters, Setters, and toString

    public Continent() {
    }

    public Continent(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}