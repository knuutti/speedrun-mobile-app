package com.example.harkkatyo;

import android.content.Context;

import java.util.ArrayList;

public class User {

    WriteJSON wJson = WriteJSON.getInstance();

    private String username;
    private String password;
    private ArrayList<Game> followedGames;
    private ArrayList<Player> followedPlayers;

    public  User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, ArrayList<Game> games, ArrayList<Player> players) {
        this.username = username;
        this.password = password;
        this.followedGames = games;
        this.followedPlayers = players;
    }

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

    public void addFollowedGame(Game game, Context context) {
        this.followedGames.add(game);
        wJson.addFollowedGame(context, this.getUsername(), game.getGameId());
    }
}
