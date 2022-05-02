/* WriteJSON.java

This code file defines methods for writing JSON styled data to file

*/

package com.example.harkkatyo;

import android.content.Context;
import android.util.JsonWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    // Method for storing users and their data to a JSON formed file
    public void writeJsonStream(Context context, ArrayList<User> users) throws IOException {
        File file = new File(context.getFilesDir(), "user_data.json");
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.setIndent("  ");
        writer.beginArray();
        for (User user : users) {
            writeUser(writer, user);
        }
        writer.endArray();
        writer.close();
    }

    // Method for storing a user to the JSON formed user data file
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

    // Method for storing followed games to JSON file
    public void writeGameArray(JsonWriter writer, ArrayList<Game> games) throws IOException {
        writer.beginArray();
        for (Game game : games) {
            writer.value(game.getGameId());
        }
        writer.endArray();
    }

    // Method for storing followed players to JSON file
    public void writePlayersArray(JsonWriter writer, ArrayList<Player> players) throws IOException {
        writer.beginArray();
        for (Player player : players) {
            writer.value(player.getPlayerId());
        }
        writer.endArray();
    }

}
