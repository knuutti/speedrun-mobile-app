package com.example.harkkatyo;

import java.util.ArrayList;

public class Game {

    private String gameId;
    private String gameName;
    private String imageUrl;
    private String releaseYear;

    private ArrayList<Category> categories;
    private ArrayList<Level> levels;

    // CONSTRUCTORS

    public Game(String gameId) {
        this.gameId = gameId;
    }

    public Game(String gameId, String gameName, String imageUrl) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString(){
        return gameName;
    }

    // GETTERS AND SETTERS

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }
}
