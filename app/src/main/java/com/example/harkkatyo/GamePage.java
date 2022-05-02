/* GamePage.java

This code file defines the functionality of the game page

*/

package com.example.harkkatyo;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class GamePage extends AppCompatActivity {

    ReadJSON rJson = ReadJSON.getInstance();
    WriteJSON wJson = WriteJSON.getInstance();

    private Game game;
    private User user;

    private ImageView ivCoverImage;
    private TextView tvGameName;
    private TextView tvGameReleaseYear;
    private TextView tvGamePlatforms;
    private TextView tvGameDeveloper;
    private ListView lvCategoryList;
    private ListView lvLevelList;
    private ImageView ivFollowIcon;
    private ImageView ivUnfollowIcon;
    private TabLayout tlType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String gameId = intent.getStringExtra("gameId");
        game = rJson.getGameData(gameId);
        user = rJson.getCurrentUser(this);

        tvGameReleaseYear = findViewById(R.id.tv_game_releaseyear_game_page);
        ivCoverImage = findViewById(R.id.iv_cover_image_game_page);
        tvGameName = findViewById(R.id.tv_game_name_game_page);
        tvGameDeveloper = findViewById(R.id.tv_game_developer_game_page);
        ivFollowIcon = findViewById(R.id.iv_followed_game_page);
        ivUnfollowIcon = findViewById(R.id.iv_unfollowed_game_page);

        if (user == null) {
            ivFollowIcon.setVisibility(View.GONE);
        }
        else {
            ArrayList<Game> followedGames = user.getFollowedGames();
            for (Game followedGame : followedGames) {
                if (followedGame.getGameId().compareTo(game.getGameId()) == 0) {
                    ivFollowIcon.setVisibility(View.GONE);
                    ivUnfollowIcon.setVisibility(View.VISIBLE);
                }
            }
        }


        setListViews();
        setTypeTabs();
        setPlatforms();

        tvGameName.setText(game.getGameName());


        if (game.getReleaseYear() != null) {
            tvGameReleaseYear.setText("Released: " + game.getReleaseYear());
        }
        if (game.getDeveloper() != null) {
            tvGameDeveloper.setText("Developer: " + game.getDeveloper());
        }

        Glide.with(this)
                .load(game.getImageUrl())
                .override(300, 200)
                .into(ivCoverImage);

    }

    // Setting up all platforms
    private void setPlatforms() {
        tvGamePlatforms = findViewById(R.id.tv_game_platforms_game_page);

        if (game.getPlatforms().size() > 0) {

            ArrayList<String> platforms = game.getPlatforms();

            String platformString = "";
            for (int i = 0; i < platforms.size(); i++) {
                if (i == platforms.size() - 1) {
                    platformString = platformString + platforms.get(i);
                } else {
                    platformString = platformString + platforms.get(i) + ", ";
                }
            }
            tvGamePlatforms.setText("Platforms: " + platformString);

        }
    }

    // Method for loading categories and levels to the listview components
    private void setListViews(){
        lvCategoryList = findViewById(R.id.lv_category_list_game_page);
        lvLevelList = findViewById(R.id.lv_level_list_game_page);

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

        lvCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GamePage.this, LeaderboardPage.class);
                intent.putExtra("uri", game.getGameId() + "/category/" + game.getCategories().get(i).getCategoryId());
                startActivity(intent);
            }
        });

        lvLevelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GamePage.this, LeaderboardPage.class);
                intent.putExtra("uri", game.getGameId()+ "/level/"+ game.getLevels().get(i).getLevelId() + "/" + game.getLevelCategories().get(0).getCategoryId());

                System.out.println(game.getLevelCategories().get(0).getCategoryName());

                startActivity(intent);
            }
        });
    }

    // Method for following a game
    public void followGame(View v) throws IOException {
        user.addFollowedGame(game);
        ArrayList<User> userArrayList = rJson.getUserList(this);
        for (int i = 0 ; i < userArrayList.size() ; i++) {
            if (user.getUsername().compareTo(userArrayList.get(i).getUsername()) == 0) {
                userArrayList.remove(i);
            }
        }
        userArrayList.add(user);
        wJson.writeJsonStream(this, userArrayList);

        ivUnfollowIcon.setVisibility(View.VISIBLE);
        ivFollowIcon.setVisibility(View.GONE);
    }

    // Method for unfollowing a game
    public void unFollowGame(View v) throws IOException {
        ArrayList<Game> userFollowedGames = user.getFollowedGames();
        for (int i = 0 ; i < userFollowedGames.size() ; i++) {
            if (game.getGameId().compareTo(userFollowedGames.get(i).getGameId()) == 0) {
                userFollowedGames.remove(i);
                user.setFollowedGames(userFollowedGames);
            }
        }
        ArrayList<User> userArrayList = rJson.getUserList(this);
        for (int i = 0 ; i < userArrayList.size() ; i++) {
            if (user.getUsername().compareTo(userArrayList.get(i).getUsername()) == 0) {
                userArrayList.remove(i);
            }
        }
        userArrayList.add(user);
        wJson.writeJsonStream(this, userArrayList);

        ivUnfollowIcon.setVisibility(View.GONE);
        ivFollowIcon.setVisibility(View.VISIBLE);
    }

    // Method for loading the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem login = menu.findItem(R.id.login);
        if (user != null) {
            login.setTitle(user.getUsername());
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

    // Method for setting tabs for Categories/Levels
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
