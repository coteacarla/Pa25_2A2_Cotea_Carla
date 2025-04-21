package org.example;

import java.io.Serializable;
import java.util.Objects;

import static java.lang.Math.sqrt;

public class Line implements Serializable {
    Dot dot1, dot2;
    int cost;

    Line(Dot a, Dot b) {
        dot1 = a;
        dot2 = b;
        cost = (int) sqrt((dot2.x - dot1.x) * (dot2.x - dot1.x) + (dot2.y - dot1.y) * (dot2.y - dot1.y));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return (Objects.equals(dot1, line.dot1) && Objects.equals(dot2, line.dot2)) ||
                (Objects.equals(dot1, line.dot2) && Objects.equals(dot2, line.dot1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(dot1.hashCode(), dot2.hashCode()), Math.max(dot1.hashCode(), dot2.hashCode()));
    }
}