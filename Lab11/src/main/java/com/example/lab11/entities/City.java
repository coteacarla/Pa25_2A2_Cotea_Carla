package com.example.lab11.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cities")
@NamedQueries({
        @NamedQuery(name = "City.findAll",
                query = "SELECT c FROM City c ORDER BY c.name"),
        @NamedQuery(name = "City.findByCountry",
                query = "SELECT c FROM City c WHERE c.country = ?1")
})
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @Column(name = "country")
    private String country;


    public City() {
    }

    public City(String name, String country) {
        this.name = name;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }
}