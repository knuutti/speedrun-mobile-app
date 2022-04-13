package com.example.harkkatyo;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private ArrayList<Game> followedGames;
    private ArrayList<Player> followedPlayers;

    public User() { }

    // GETTERS AND SETTERS

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Game> getFollowedGames() {
        return followedGames;
    }

    public void setFollowedGames(ArrayList<Game> followedGames) {
        this.followedGames = followedGames;
    }

    public ArrayList<Player> getFollowedPlayers() {
        return followedPlayers;
    }

    public void setFollowedPlayers(ArrayList<Player> followedPlayers) {
        this.followedPlayers = followedPlayers;
    }
}
