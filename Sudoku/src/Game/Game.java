package Game;

import java.io.Serializable;

public class Game implements Serializable , Comparable<Game> {
    private long time;
    private String name = null;
    private int mistakes;
    private String difficulty;


    public Game(long time, String name, int mistakes , String difficulty) {
        this.time = time;
        this.name = name;
        this.mistakes = mistakes;
        this.difficulty = difficulty;
    }

    public String toString(){
        return String.format("%-15s %4d %10d s.         %s",name,mistakes,time,difficulty);
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getMistakes() {
        return mistakes;
    }

    @Override
    public int compareTo(Game o) {
        if(this.getTime()<o.getTime()) return -1;
        if(this.getTime()>o.getTime()) return 1;
        else return 0;
    }
}
