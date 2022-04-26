package com.example.harkkatyo;

import android.content.Context;
import android.util.JsonWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class WriteJSON {

    private static WriteJSON writeJSON;

    private WriteJSON(){}

    public static WriteJSON getInstance(){
        if (writeJSON == null) {
            writeJSON = new WriteJSON();
        }
        return writeJSON;
    }

    public void writeJsonStream(OutputStream out, ArrayList<User> users) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("  ");
        writeUsersArray(writer, users);
        writer.close();
    }

    public void addFollowedGame(Context context, String username, String gameId) {
        JSONParser parser = new JSONParser();
        File file = new File(context.getFilesDir(), "user_data.json");

        try (FileReader reader = new FileReader(file))
        {
            JSONArray obj = (JSONArray) parser.parse(reader);

            for (int i = 0 ; i < obj.size() ; i++) {
                JSONObject user = (JSONObject) obj.get(i);
                String usernameInstance = user.get("username").toString();
                if (usernameInstance.compareTo(username) == 0) {
                    JSONArray followedGames = (JSONArray) user.get("followed_games");
                    JSONObject j1 = new JSONObject();
                    j1.put("id", gameId);
                    followedGames.add(j1);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void writeUsersArray(JsonWriter writer, ArrayList<User> users) throws IOException {
        writer.beginArray();
        for (User user : users) {
            writeUser(writer, user);
        }
        writer.endArray();
    }

    public void writeUser(JsonWriter writer, User user) throws IOException {
        writer.beginObject();
        writer.name("username").value(user.getUsername());
        writer.name("password").value(user.getPassword());
        if (user.getFollowedGames() != null) {
            writer.name("followed_games");
            writeGameArray(writer, user.getFollowedGames());
        } else {
            writer.name("followed_games").nullValue();
        }
        if (user.getFollowedPlayers() != null) {
            writer.name("followed_players");
            writePlayersArray(writer, user.getFollowedPlayers());
        } else {
            writer.name("followed_games").nullValue();
        }
        writer.endObject();
    }

    public void writeGameArray(JsonWriter writer, ArrayList<Game> games) throws IOException {
        writer.beginArray();
        for (Game game : games) {
            writer.value(game.getGameId());
        }
        writer.endArray();
    }

    public void writePlayersArray(JsonWriter writer, ArrayList<Player> players) throws IOException {
        writer.beginArray();
        for (Player player : players) {
            writer.value(player.getPlayerId());
        }
        writer.endArray();
    }

}
