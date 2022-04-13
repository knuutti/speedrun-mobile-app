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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

    public Leaderboard getLeaderboardData(Game game, Category category, int start, int end){

        String leaderboardJSON = JsonToString("https://www.speedrun.com/api/v1/leaderboards/" + game.getGameId() + "/category/" + category.getCategoryId());

        Leaderboard leaderboard = Leaderboard.getInstance();
        leaderboard.clear();

        String place;
        String runId;

        if (leaderboardJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(leaderboardJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONArray runs = (JSONArray) data.get("runs");

                for (int i = start; i<(runs.size()) && i < end; i++) {
                    JSONObject runObject = (JSONObject) runs.get(i);
                    JSONObject runJson = (JSONObject) runObject.get("run");

                    place = runObject.get("place").toString();
                    runId = runJson.get("id").toString();

                    Run run = getRunData(place, runId);

                    leaderboard.addRun(run);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return leaderboard;
    }

    public Leaderboard getLevelLeaderboardData(Game game, Level level, Category category, int start, int end){

        String leaderboardJSON = JsonToString("https://www.speedrun.com/api/v1/leaderboards/" + game.getGameId() + "/level/" + level.getLevelId() + "/" + category.getCategoryId());

        Leaderboard leaderboard = Leaderboard.getInstance();
        leaderboard.clear();

        String place;
        String runId;

        if (leaderboardJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(leaderboardJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONArray runs = (JSONArray) data.get("runs");

                for (int i = start; i<(runs.size()) && i < end; i++) {
                    JSONObject runObject = (JSONObject) runs.get(i);
                    JSONObject runJson = (JSONObject) runObject.get("run");

                    place = runObject.get("place").toString();
                    runId = runJson.get("id").toString();

                    Run run = getRunData(place, runId);

                    leaderboard.addRun(run);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return leaderboard;
    }

    // Method for creating a Run object from run id
    public Run getRunData(String place, String runId) {

        String runJson = JsonToString("https://www.speedrun.com/api/v1/runs/" + runId);

        String playerId = null;
        String gameId = null;
        String date = null;
        String realtime = null;
        String ingame = null;
        String playerName = null;

        if (runJson != null) {
            try {
                JSONParser parser = new JSONParser();

                JSONObject obj = (JSONObject) parser.parse(runJson);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject times = (JSONObject) data.get("times");
                JSONArray players = (JSONArray) data.get("players");
                JSONObject playerObject = (JSONObject) players.get(0);

                gameId = data.get("game").toString();
                date = data.get("date").toString();
                realtime = times.get("realtime_t").toString();
                if (realtime.compareTo("0") == 0) {
                    ingame = times.get("ingame_t").toString();
                }

                // Checks if the runner has an account
                if (playerObject.get("rel").toString().compareTo("user") == 0) {
                    playerId = playerObject.get("id").toString();
                }
                else {
                    playerName = playerObject.get("name").toString();
                }

            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        Run run = new Run(place, runId, gameId, playerId, playerName, date, realtime, ingame);
        return run;
    }

    // Method for creating a Player object from player id
    public Player getPlayerData(String playerId){

        String playerJSON = JsonToString("https://www.speedrun.com/api/v1/users/" + playerId);

        String playerName = "Player";
        String country = "default";
        String colorStart = "#FFFFFF";
        String colorEnd = "#FFFFFF";

        if (playerJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(playerJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject names = (JSONObject) data.get("names");
                JSONObject nameStyle = (JSONObject) data.get("name-style");
                JSONObject colorFrom = (JSONObject) nameStyle.get("color-from");
                JSONObject colorTo = (JSONObject) nameStyle.get("color-to");
                JSONObject location = (JSONObject) data.get("location");

                playerName = names.get("international").toString();
                colorEnd = colorTo.get("dark").toString();
                colorStart = colorFrom.get("dark").toString();

                if (location != null) {
                    JSONObject countryJson = (JSONObject) location.get("country");
                    country = countryJson.get("code").toString();
                }
                else {
                    country = "default";
                }

            } catch (ParseException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        Player player = new Player(playerId, playerName, country, colorStart, colorEnd);

        return player;
    }

    public ArrayList getCategoryData(String gameId) {
        String categoryJSON = JsonToString("https://www.speedrun.com/api/v1/games/" + gameId + "/categories");
        ArrayList<Category> categories = new ArrayList<>();
        if (categoryJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(categoryJSON);
                JSONArray data = (JSONArray) obj.get("data");

                for (int i = 0; i < data.size(); i++) {
                    JSONObject categoryObj = (JSONObject) data.get(i);
                    String categoryId = categoryObj.get("id").toString();
                    String categoryName = categoryObj.get("name").toString();
                    categories.add(new Category(categoryId, categoryName));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public ArrayList getLevelCategoryData(String level_id) {
        String categoryJSON = JsonToString("https://www.speedrun.com/api/v1/levels/" + level_id + "/categories");
        ArrayList<Category> categories = new ArrayList<>();
        if (categoryJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(categoryJSON);
                JSONArray data = (JSONArray) obj.get("data");

                for (int i = 0; i < data.size(); i++) {
                    JSONObject categoryObj = (JSONObject) data.get(i);
                    String id = categoryObj.get("id").toString();
                    String name = categoryObj.get("name").toString();
                    categories.add(new Category(id, name));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    public ArrayList getLevelData(String game_id) {
        String categoryJSON = JsonToString("https://www.speedrun.com/api/v1/games/" + game_id + "/levels");
        ArrayList<Level> levels = new ArrayList<>();
        if (categoryJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(categoryJSON);
                JSONArray data = (JSONArray) obj.get("data");

                for (int i = 0; i < data.size(); i++) {
                    JSONObject categoryObj = (JSONObject) data.get(i);
                    String levelId = categoryObj.get("id").toString();
                    String levelName = categoryObj.get("name").toString();
                    levels.add(new Level(levelId, levelName));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return levels;
    }

    public String[] getGameData(String game_id) {
        String gameJSON = getGameDataJSON(game_id);
        String[] data_array = new String[3];

        if (gameJSON != null) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(gameJSON);
                JSONObject data = (JSONObject) obj.get("data");
                JSONObject names = (JSONObject) data.get("names");
                JSONObject assets = (JSONObject) data.get("assets");
                JSONObject cover = (JSONObject) assets.get("cover-medium");
                data_array[0] = names.get("international").toString();
                data_array[1] = cover.get("uri").toString();
                data_array[2] = data.get("released").toString();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return data_array;
    }

    private String getGameDataJSON(String game_id){
        String response = JsonToString("https://www.speedrun.com/api/v1/games/" + game_id);
        return response;
    }

}
