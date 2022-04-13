package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ReadJSON json = ReadJSON.getInstance();

    private EditText etGameSearch;
    protected ListView lvGameList;

    private ArrayList<Game> gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etGameSearch = findViewById(R.id.etGameSearch);
        lvGameList = findViewById(R.id.lvGameList);
    }

    // Method for searching games based on user input
    public void searchGames(View v) {
        gameList = json.gameSearch(etGameSearch.getText().toString());
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

}