package com.example.lab11.entities;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
@NamedQueries({
        @NamedQuery(name = "Country.findAll",
                query = "SELECT c FROM Country c ORDER BY c.name"),
        @NamedQuery(name = "Country.findByContinent",
                query = "SELECT c FROM Country c WHERE c.continent = ?1")
})
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "continent_id", referencedColumnName = "id", nullable = false)
    private Continent continent;

    @ManyToMany
    @JoinTable(
            name = "country_neighbors",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "neighbor_id")
    )
    private List<Country> neighbors = new ArrayList<>();


    // Constructors, Getters, Setters, and toString

    public Country() {
    }

    public Country(String name, String code, Continent continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public List<Country> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Country> neighbors) {
        this.neighbors = neighbors;
    }


    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}