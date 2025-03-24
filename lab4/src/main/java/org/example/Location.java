package org.example;
import lombok.*;

import javax.management.ConstructorParameters;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Location implements Comparable<Location> {
    private String name;
    private LocationType type;

    @Override
    public int compareTo(Location other) {
        return this.name.compareTo(other.name);
    }

}
