package org.example;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Pair<L1, L2> {
    private L1 from;
    private L2 to;
    private double time;
    private double safetyProbability;
}