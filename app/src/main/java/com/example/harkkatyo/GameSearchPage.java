package com.example.harkkatyo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameSearchPage extends AppCompatActivity {

    ReadJSON rJson = ReadJSON.getInstance();

    private EditText etGameSearch;
    private ListView lvGameList;
    private Switch sUnofficialReleases;

    private ArrayList<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_search_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etGameSearch = findViewById(R.id.etGameSearch);
        lvGameList = findViewById(R.id.lvGameList);
        sUnofficialReleases = findViewById(R.id.sUnofficialReleases);

        lvGameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GameSearchPage.this, GamePage.class);
                intent.putExtra("gameId", gameList.get(i).getGameId());
                startActivity(intent);
            }
        });

    }

    // Method for searching games based on user input
    public void searchGames(View v) {
        if (sUnofficialReleases.isChecked() == false) {
            gameList = rJson.gameSearch(etGameSearch.getText().toString() + "&romhack=false");
        }
        else {
            gameList = rJson.gameSearch(etGameSearch.getText().toString());
        }
        setGameList();
    }

    // Method for updating the list of games shown
    private void setGameList(){
        if (gameList != null) {
            GameListAdapter adapter = new GameListAdapter(this, R.layout.game_list_view, gameList);
            lvGameList.setAdapter(adapter);
        }

    }

    // This makes EditText element to lose focus after clicking somewhere else
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

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
