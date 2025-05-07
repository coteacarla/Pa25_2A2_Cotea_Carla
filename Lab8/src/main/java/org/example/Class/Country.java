package org.example.Class;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode (callSuper = false)
public class Country extends Location {
    private int id;
    private String name;
    private int code;
    private int continent_id;
}