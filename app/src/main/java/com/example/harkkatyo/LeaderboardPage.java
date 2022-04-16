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

    private ArrayList<Run> runs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");

        lvLeaderboard = findViewById(R.id.lv_leaderboard_leaderboard_page);

        runs = json.getLeaderboardData(uri);

        if (runs != null) {
            LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.leaderboard_list_view, runs);
            lvLeaderboard.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Setting up action bar functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.login:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
