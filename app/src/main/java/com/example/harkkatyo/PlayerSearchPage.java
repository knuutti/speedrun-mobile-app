package com.example.harkkatyo;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlayerSearchPage extends AppCompatActivity {

    ReadJSON json = ReadJSON.getInstance();

    private EditText etPlayerSearch;
    private ListView lvPlayerList;

    private ArrayList<Player> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_search_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etPlayerSearch = findViewById(R.id.etPlayerSearch);
        lvPlayerList = findViewById(R.id.lvPlayerList);
    }

    public void searchPlayers(View v) {
        playerList = json.playerSearch(etPlayerSearch.getText().toString());
        setPlayerList();
    }

    private void setPlayerList(){
        if (playerList != null) {
            PlayerListAdapter adapter = new PlayerListAdapter(this, R.layout.player_list_view, playerList);
            lvPlayerList.setAdapter(adapter);
        }
    }

}
