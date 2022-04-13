package com.example.harkkatyo;

import java.util.ArrayList;

public class Leaderboard {

    private static Leaderboard lb;

    private ArrayList<Run> leaderboard;

    private Leaderboard(){}

    public static Leaderboard getInstance(){
        if (lb == null) {
            lb = new Leaderboard();
        }
        return lb;
    }

    public void addRun(Run run){
        leaderboard.add(run);
    }

    public void clear() {
        leaderboard.clear();
    }

    // GETTERS AND SETTERS

    public ArrayList<Run> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(ArrayList<Run> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
