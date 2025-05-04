package org.example;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode (callSuper = false)
public class City extends Location{
    int id;
    String name;
    String country;
    boolean capital=true;
    double latitude;
    double longitude;

}
