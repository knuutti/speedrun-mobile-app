package com.example.harkkatyo;

import java.util.ArrayList;

public class Player {

    private String playerId;
    private String playerName;
    private String country = "default";
    private String colorStart = "#FFFFFF";
    private String colorEnd = "#FFFFFF";

    private ArrayList<Run> personalBests;

    // CONSTRUCTORS

    public Player(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public Player(String playerId, String playerName, String country, String colorStart, String colorEnd) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.country = country;
        this.colorStart = colorStart;
        this.colorEnd = colorEnd;
    }

    // GETTERS AND SETTERS

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getColorStart() {
        return colorStart;
    }

    public void setColorStart(String colorStart) {
        this.colorStart = colorStart;
    }

    public String getColorEnd() {
        return colorEnd;
    }

    public void setColorEnd(String colorEnd) {
        this.colorEnd = colorEnd;
    }

    public ArrayList<Run> getPersonalBests() {
        return personalBests;
    }

    public void setPersonalBests(ArrayList<Run> personalBests) {
        this.personalBests = personalBests;
    }
}
