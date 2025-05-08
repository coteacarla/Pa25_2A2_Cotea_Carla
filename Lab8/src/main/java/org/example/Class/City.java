package org.example.Class;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode (callSuper = false)
public class City extends Location {
    int id;
    String name;
    String country;
    boolean capital=true;
    double latitude;
    double longitude;
    public City(int id, String name, String country, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}