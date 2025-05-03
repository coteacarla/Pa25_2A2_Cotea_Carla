package org.example;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode (callSuper = false)
public class Continent extends Location{
    private int id;
    private String name;
}
