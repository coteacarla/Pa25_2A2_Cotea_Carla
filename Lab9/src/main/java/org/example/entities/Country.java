package org.example.entities;

import jakarta.persistence.*;
import java.io.Serializable;

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

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
