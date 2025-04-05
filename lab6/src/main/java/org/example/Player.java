package org.example;

import java.util.List;

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
        score+=(int)sqrt((b.x-a.x)*(b.x-a.x)+(b.y-a.y)*(b.y-a.y));
        System.out.println(score);
    }

    public int bestScore(List<Dot> dots) {

        int maxscore = 0;
        for (Dot dot1 : dots) {
            for (Dot dot2 : dots) {
                int currentscore=(int)sqrt((dot2.x-dot1.x)*(dot2.x-dot1.x)+(dot2.y-dot1.y)*(dot2.y-dot1.y));
                if(currentscore>maxscore)
                    maxscore=currentscore;
            }
        }
        System.out.println(maxscore);
        return maxscore;
    }
}
