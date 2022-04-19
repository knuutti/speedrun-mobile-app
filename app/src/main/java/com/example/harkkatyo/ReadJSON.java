package com.example.harkkatyo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ReadJSON {

    private static ReadJSON readJSON;

    private ReadJSON(){}

    public static ReadJSON getInstance(){
        if (readJSON == null) {
            readJSON = new ReadJSON();
        }
        return readJSON;
    }

    // This methods reads the JSON file, turns it into a string and returns the string
    private String JsonToString(String urlString) {
        String response = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

            response = sb.toString();
            in.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    // Function for searching games by the search words user gives
    public ArrayList<Game> gameSearch(String searchWords) {
        ArrayList<Game> gameList = new ArrayList<>();
        StringBuilder searchUrl = new StringBuilder("https://www.speedrun.com/api/v1/games?name=");

        // Replaces spaces with %20 for the search
        String newSearchWords = searchWords.replace(" ", "%20");
        searchUrl.append(newSearchWords);

        String searchResult = JsonToString(searchUrl.toString());

        if (searchResult != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(searchResult);
                JSONArray data = (JSONArray) obj.get("data");

                // Each game of the search result
                for (int i = 0 ; i < data.size() ; i++) {
                    JSONObject gameInstance = (JSONObject) data.get(i);
                    String gameId = gameInstance.get("id").toString();
                    JSONObject names = (JSONObject) gameInstance.get("names");
                    String gameName = names.get("international").toString();
                    JSONObject assets = (JSONObject) gameInstance.get("assets");
                    JSONObject coverImage = (JSONObject) assets.get("cover-tiny");
                    String imageUrl = coverImage.get("uri").toString();

                    Game game = new Game(gameId, gameName, imageUrl);

                    gameList.add(game);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return gameList;
    }

    public ArrayList<Run> getLeaderboardData(String uri){

        String leaderboardJSON = JsonToString("https://www.speedrun.com/api/v1/leaderboards/" + uri + "?embed=players,game");

        ArrayList<Run> leaderboard = new ArrayList<>();

        if (leaderboardJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(leaderboardJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject players = (JSONObject) data.get("players");
                JSONObject game = (JSONObject) data.get("game");

                JSONArray runList = (JSONArray) data.get("runs");
                JSONArray playerList =(JSONArray) players.get("data");

                // Loop for going through each run on the leaderboard
                for (int i = 0 ; i < runList.size() ; i++) {
                    JSONObject runObject = (JSONObject) runList.get(i);
                    JSONObject playerObject = (JSONObject) playerList.get(i);

                    // Run related data
                    JSONObject runData = (JSONObject) runObject.get("run");
                    JSONObject times = (JSONObject) runData.get("times");

                    String place = runObject.get("place").toString();
                    String runId = runData.get("id").toString();

                    String date = "";
                    if (runData.get("date") != null) {
                        date = runData.get("date").toString();
                    }
                    String time = times.get("primary_t").toString();

                    // Player related data

                    String colorFrom = "#FFFFFF";
                    String colorTo = "#FFFFFF";
                    String flag = "default";
                    String playerName = null;
                    String playerId = null;
                    String trophyUrl = null;

                    // Condition for separating registered users from guests
                    if (playerObject.get("rel").toString().compareTo("user") == 0) {
                        JSONObject names = (JSONObject) playerObject.get("names");
                        JSONObject nameStyle = (JSONObject) playerObject.get("name-style");

                        if (nameStyle.get("style").toString().compareTo("gradient") == 0) {
                            JSONObject colorF = (JSONObject) nameStyle.get("color-from");
                            JSONObject colorT = (JSONObject) nameStyle.get("color-to");
                            colorFrom = colorF.get("dark").toString();
                            colorTo = colorT.get("dark").toString();
                        } else if (nameStyle.get("style").toString().compareTo("solid") == 0) {
                            JSONObject colorObj = (JSONObject) nameStyle.get("color");
                            colorFrom = colorObj.get("dark").toString();
                            colorTo = colorFrom;
                        }

                        JSONObject location = (JSONObject) playerObject.get("location");
                        if (location != null) {
                            JSONObject country = (JSONObject) location.get("country");
                            flag = country.get("code").toString();
                        }

                        playerName = names.get("international").toString();
                        playerId = playerObject.get("id").toString();

                    }
                    else {
                        playerName = playerObject.get("name").toString();
                    }

                    // Trophy for top-placements
                    JSONObject gameData = (JSONObject) game.get("data");
                    JSONObject gameAssets = (JSONObject) gameData.get("assets");
                    JSONObject trophy;
                    if (place.compareTo("1") == 0 && gameAssets.get("trophy-1st") != null) {
                        trophy = (JSONObject) gameAssets.get("trophy-1st");
                        trophyUrl = trophy.get("uri").toString();
                    }
                    else if (place.compareTo("2") == 0 && gameAssets.get("trophy-2nd") != null) {
                        trophy = (JSONObject) gameAssets.get("trophy-2nd");
                        trophyUrl = trophy.get("uri").toString();
                    }
                    else if (place.compareTo("3") == 0 && gameAssets.get("trophy-3rd") != null) {
                        trophy = (JSONObject) gameAssets.get("trophy-3rd");
                        trophyUrl = trophy.get("uri").toString();
                    }
                    else if (place.compareTo("4") == 0 && gameAssets.get("trophy-4th") != null) {
                        trophy = (JSONObject) gameAssets.get("trophy-4th");
                        if (trophy.get("uri") != null) {
                            trophyUrl = trophy.get("uri").toString();
                        }
                    }

                    Run run = new Run(place, runId, time, date, trophyUrl, new Player(playerId, playerName, flag, colorFrom, colorTo));

                    leaderboard.add(run);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return leaderboard;
    }

    // Function for getting required date for a game based on it's ID
    public Game getGameData(String gameId) {
        String gameJSON = JsonToString("https://www.speedrun.com/api/v1/games/" + gameId + "?embed=categories,levels");

        String gameName = null;
        String imageUrl = null;
        String releaseYear = null;
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        ArrayList<Level> levelArrayList = new ArrayList<>();
        ArrayList<Category> levelCategoryArrayList = new ArrayList<>();

        if (gameJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(gameJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject categories = (JSONObject) data.get("categories");
                JSONObject levels = (JSONObject) data.get("levels");

                JSONArray categoryData = (JSONArray) categories.get("data");

                for (int i = 0; i < categoryData.size(); i++) {
                    JSONObject categoryObj = (JSONObject) categoryData.get(i);
                    String categoryId = categoryObj.get("id").toString();
                    String categoryName = categoryObj.get("name").toString();

                    // Condition for separating level categories
                    if (categoryObj.get("type").toString().compareTo("per-level") != 0) {
                        categoryArrayList.add(new Category(categoryId, categoryName));
                    }
                    else {
                        levelCategoryArrayList.add(new Category(categoryId, categoryName));
                    }
                }

                JSONArray levelData = (JSONArray) levels.get("data");

                for (int i = 0; i < levelData.size(); i++) {
                    JSONObject categoryObj = (JSONObject) levelData.get(i);
                    String levelId = categoryObj.get("id").toString();
                    String levelName = Objects.requireNonNull(categoryObj.get("name")).toString();
                    levelArrayList.add(new Level(levelId, levelName));
                }

                JSONObject names = (JSONObject) data.get("names");
                JSONObject assets = (JSONObject) data.get("assets");
                JSONObject cover = (JSONObject) assets.get("cover-medium");
                gameName = names.get("international").toString();
                imageUrl = cover.get("uri").toString();
                releaseYear = data.get("released").toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Game game = new Game(gameId, gameName, imageUrl, releaseYear, categoryArrayList, levelArrayList, levelCategoryArrayList);

        return game;
    }

}