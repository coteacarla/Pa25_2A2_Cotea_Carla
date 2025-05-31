package com.example.lab11.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "country_neighbors",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "neighbor_id")
    )
    private List<Country> neighbors = new ArrayList<>();

    @ManyToMany(mappedBy = "neighbors", fetch = FetchType.EAGER)
    private List<Country> inverseNeighbors = new ArrayList<>();


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

    public List<Country> getInverseNeighbors() {
        return inverseNeighbors;
    }

    public void setInverseNeighbors(List<Country> inverseNeighbors) {
        this.inverseNeighbors = inverseNeighbors;
    }

    public Set<Country> getAllNeighbors() {
        Set<Country> all = new HashSet<>(neighbors);
        all.addAll(inverseNeighbors);
        return all;
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
