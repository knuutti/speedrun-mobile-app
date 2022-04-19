package com.example.harkkatyo;

import android.util.JsonWriter;

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
