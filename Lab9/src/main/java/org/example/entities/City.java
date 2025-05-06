package org.example.entities;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;


    public City() {
    }

    public City(String name, Country country) {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
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
