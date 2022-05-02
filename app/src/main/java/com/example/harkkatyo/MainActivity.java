/* MainActivity.java

This code file defines the functionality of the main page (default page when booting the app)

*/

package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WriteJSON wJson = WriteJSON.getInstance();
    ReadJSON rJson = ReadJSON.getInstance();

    private User currentUser;

    private ListView lvFollowedGames;
    private ListView lvFollowedPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        lvFollowedGames = findViewById(R.id.lv_followed_games);
        lvFollowedPlayers = findViewById(R.id.lv_followed_players);

        setUserData();
        currentUser = rJson.getCurrentUser(this);
        setFollowedGamesAndPlayers();

    }

    // Method for setting the followed players and games if logged user has any
    public void setFollowedGamesAndPlayers() {
        if (currentUser != null) {
            ArrayList<Game> followedGames = new ArrayList<>();
            for (Game game : currentUser.getFollowedGames()) {
                followedGames.add(rJson.getGameData(game.getGameId()));
            }

            ArrayList<Player> followedPlayers = new ArrayList<>();
            for (Player player : currentUser.getFollowedPlayers()) {
                followedPlayers.add(rJson.getPlayerData(player.getPlayerId()));
            }

            if (followedGames != null) {
                GameListAdapter adapter = new GameListAdapter(this, R.layout.game_list_view, followedGames);
                lvFollowedGames.setAdapter(adapter);
            }

            lvFollowedGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, GamePage.class);
                    intent.putExtra("gameId", followedGames.get(i).getGameId());
                    startActivity(intent);
                }
            });

            if (followedPlayers != null) {
                PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.player_list_view, followedPlayers);
                lvFollowedPlayers.setAdapter(adapter);
            }

            lvFollowedPlayers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, PlayerPage.class);
                    intent.putExtra("playerId", followedPlayers.get(i).getPlayerId());
                    startActivity(intent);
                }
            });
        }
    }

    // Checks if there is a file for user data, creates one if needed
    public void setUserData(){
        File userDataFile = new File(getApplicationContext().getFilesDir(), "user_data.json");

        if (userDataFile.exists() == false) {
            ArrayList<User> users = new ArrayList<>();

            try {
                wJson.writeJsonStream(this.getApplicationContext(), users);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Creating a file for storing the current user
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("current_user.txt", Context.MODE_PRIVATE));
                outputStreamWriter.write("");
                outputStreamWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method for loading the menu bar (same for every page)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem login = menu.findItem(R.id.login);
        if (currentUser != null) {
            login.setTitle(currentUser.getUsername());
        }
        else {
            login.setTitle("LOGIN");
        }
        return super.onCreateOptionsMenu(menu);
    }

    // Method for defining the functionality of the menu bar (same for every page)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.search:
                Intent searchIntent = new Intent(getApplicationContext(), GameSearchPage.class);
                startActivity(searchIntent);
                return true;
            case R.id.login:
                Intent loginIntent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(loginIntent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // Method for loading the game search page (button)
    public void goToGameSearchPage(View v) {
        Intent intent = new Intent(MainActivity.this, GameSearchPage.class);
        startActivity(intent);
    }

    // Method for loading the player search page (button)
    public void goToPlayerSearchPage(View v) {
        Intent intent = new Intent(MainActivity.this, PlayerSearchPage.class);
        startActivity(intent);
    }

    // Method for loading the official website on a web browser (button)
    public void imageWeb(View v) {
       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.speedrun.com"));
       startActivity(browserIntent);
    }

}