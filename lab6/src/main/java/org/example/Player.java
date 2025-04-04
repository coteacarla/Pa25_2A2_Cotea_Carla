package org.example;

import static java.lang.Math.sqrt;

public class Player {
    private int score;
    public Player() {
        score = 0;
    }
    public int getScore() {
        return score;
    }
    public void addScore( Dot a, Dot b ) {
        score=(int)sqrt((a.y-a.x)^2 +(b.y-b.x)^2)+score;
        System.out.println(score);
    }
}
