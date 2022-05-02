/* Game.java

This code file defines the Game class

*/

package com.example.harkkatyo;

import java.util.ArrayList;

public class Game {

    private String gameId;
    private String gameName;
    private String imageUrl;
    private String releaseYear;
    private String developer;

    private ArrayList<Category> categories;
    private ArrayList<Level> levels;
    private ArrayList<Category> levelCategories;
    private ArrayList<String> platforms;

    // CONSTRUCTORS

    public Game(String gameId) {
        this.gameId = gameId;
    }

    public Game(String gameId, String gameName, String releaseYear, String imageUrl) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.releaseYear = releaseYear;
        this.imageUrl = imageUrl;
    }

    public Game(String gameId, String gameName, String imageUrl, String releaseYear, String developer, ArrayList categories, ArrayList levels, ArrayList levelCategories, ArrayList platforms) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
        this.developer = developer;
        this.categories = categories;
        this.levels = levels;
        this.levelCategories = levelCategories;
        this.platforms = platforms;
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

    public ArrayList<Category> getLevelCategories() {
        return levelCategories;
    }

    public void setLevelCategories(ArrayList<Category> levelCategories) {
        this.levelCategories = levelCategories;
    }

    public ArrayList<String> getPlatforms() { return platforms;}

    public void setPlatforms(ArrayList<String> platforms) {this.platforms = platforms;}

    public String getDeveloper() {return developer;}

    public void setDeveloper(String developer) {this.developer = developer;}
}
