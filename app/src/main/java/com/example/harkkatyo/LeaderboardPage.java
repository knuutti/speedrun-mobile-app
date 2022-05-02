/* LeaderboardPage.java

This code file defines the functionality of the leaderboard page

*/

package com.example.harkkatyo;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LeaderboardPage extends AppCompatActivity {

    ReadJSON json = ReadJSON.getInstance();

    private ListView lvLeaderboard;

    private User currentUser;

    private ArrayList<Run> runs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        currentUser = json.getCurrentUser(this);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");

        lvLeaderboard = findViewById(R.id.lv_leaderboard_leaderboard_page);

        runs = json.getLeaderboardData(uri);

        if (runs != null) {
            LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.leaderboard_list_view, runs);
            lvLeaderboard.setAdapter(adapter);
        }

    }

    // Method for loading the menu
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

    // Method for defining the menu functionality
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

}
