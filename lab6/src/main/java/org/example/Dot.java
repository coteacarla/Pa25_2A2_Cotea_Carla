package org.example;

import java.io.Serializable;

public class Dot implements Serializable {
    int x;
    int y;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean contains(int clickX, int clickY, int size) {
        return (clickX >= x - size && clickX <= x + size) &&
                (clickY >= y - size && clickY <= y + size);
    }

}