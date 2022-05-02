/* Player.java

This code file defines the Player class

*/

package com.example.harkkatyo;

import java.util.ArrayList;

public class Player {

    private String playerId;
    private String playerName;
    private String countryCode = "default";
    private String colorStart = "#FFFFFF";
    private String colorEnd = "#FFFFFF";
    private String webLink;
    private String countryName;
    private String twitchAcc;
    private String youtubeAcc;


    private ArrayList<Run> personalBests;

    // CONSTRUCTORS

    public Player(String playerId) {
        this.playerId = playerId;
    }

    public Player(String playerId, String playerName, String countryCode, String colorStart, String colorEnd) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.countryCode = countryCode;
        this.colorStart = colorStart;
        this.colorEnd = colorEnd;
    }

    public Player(String playerId, String playerName, String countryCode) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.countryCode = countryCode;
    }

    public Player(String playerId, String playerName, String countryCode, String colorStart, String colorEnd, String webLink, String countryName, String twitchAcc, String youtubeAcc) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.countryCode = countryCode;
        this.colorStart = colorStart;
        this.colorEnd = colorEnd;
        this.webLink = webLink;
        this.countryName = countryName;
        this.twitchAcc = twitchAcc;
        this.youtubeAcc = youtubeAcc;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getWebLink() {return webLink;}

    public void setWebLink(String webLink) {this.webLink = webLink;}

    public String getCountryName() {return countryName;}

    public void setCountryName() {this.countryName = countryName;}

    public String getTwitchAcc() {return twitchAcc;}

    public void setTwitchAcc(String twitchAcc) {this.twitchAcc = twitchAcc;}

    public String getYoutubeAcc() {return youtubeAcc;}

    public void setYoutubeAcc(String youtubeAcc) {this.youtubeAcc = youtubeAcc;}


}
