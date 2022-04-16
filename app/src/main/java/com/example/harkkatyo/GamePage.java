package com.example.harkkatyo;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

public class GamePage extends AppCompatActivity {

    ReadJSON json = ReadJSON.getInstance();

    private Game game;

    private ImageView ivCoverImage;
    private TextView tvGameName;
    private ListView lvCategoryList;
    private ListView lvLevelList;
    private TabLayout tlType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String gameId = intent.getStringExtra("gameId");
        game = json.getGameData(gameId);

        ivCoverImage = findViewById(R.id.iv_cover_image_game_page);
        tvGameName = findViewById(R.id.tv_game_name_game_page);
        lvCategoryList = findViewById(R.id.lv_category_list_game_page);
        lvLevelList = findViewById(R.id.lv_level_list_game_page);

        setTypeTabs();

        tvGameName.setText(game.getGameName());
        Glide.with(this)
                .load(game.getImageUrl())
                .override(300, 200)
                .into(ivCoverImage);

        // Updates categories to the category list
        if (game.getCategories() != null) {
            CategoryListAdapter adapter = new CategoryListAdapter(this, R.layout.custom_list_view, game.getCategories());
            lvCategoryList.setAdapter(adapter);
        }

        // Updates levels to the level list (invisible by default)
        if (game.getLevels() != null) {
            LevelListAdapter adapter = new LevelListAdapter(this, R.layout.custom_list_view, game.getLevels());
            lvLevelList.setAdapter(adapter);
        }

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.login:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void setTypeTabs() {
        tlType = findViewById(R.id.tlType);

        tlType.addTab(tlType.newTab().setText("Categories"));
        tlType.addTab(tlType.newTab().setText("Levels"));

        tlType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int index = tlType.getSelectedTabPosition();
                if (index == 0 && lvCategoryList.getVisibility() == View.GONE) {
                    lvLevelList.setVisibility(View.GONE);
                    lvCategoryList.setVisibility(View.VISIBLE);
                }
                else if (index == 1 && lvLevelList.getVisibility() == View.GONE) {
                    lvCategoryList.setVisibility(View.GONE);
                    lvLevelList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
