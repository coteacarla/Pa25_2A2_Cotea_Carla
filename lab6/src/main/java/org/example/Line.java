package org.example;

import java.io.Serializable;
import static java.lang.Math.sqrt;

public class Line implements Serializable  {
        Dot dot1, dot2;
        int cost;

        Line(Dot a, Dot b) {
            dot1 = a;
            dot2 = b;
            cost = (int)sqrt((dot2.x-dot1.x)*(dot2.x-dot1.x)+(dot2.y-dot1.y)*(dot2.y-dot1.y));
        }

}
