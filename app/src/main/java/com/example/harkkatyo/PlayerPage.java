package com.example.harkkatyo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerPage extends AppCompatActivity {

    ReadJSON rJson = ReadJSON.getInstance();
    WriteJSON wJson = WriteJSON.getInstance();

    private Player player;
    private User user;

    private ImageView ivProfilePic;
    private TextView tvPlayerName;
    private TextView tvLocation;
    private TextView tvWebLink;
    private TextView tvYoutubeLink;
    private TextView tvTwitchLink;
    private ImageView ivFollowIcon;
    private ImageView ivUnFollowIcon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String playerId = intent.getStringExtra("playerId");
        player = rJson.getPlayerData(playerId);
        user = rJson.getCurrentUser(this);

        tvPlayerName = findViewById(R.id.tv_player_name_player_page);
        tvLocation = findViewById(R.id.tv_player_location_player_page);
        tvWebLink = findViewById(R.id.tv_player_speedrun_player_page);
        tvYoutubeLink = findViewById(R.id.tv_player_youtube_player_page);
        tvTwitchLink = findViewById(R.id.tv_player_twitch_player_page);
        ivProfilePic = findViewById(R.id.iv_cover_image_player_page);
        ivFollowIcon = findViewById(R.id.iv_followed_player_page);
        ivUnFollowIcon = findViewById(R.id.iv_unfollowed_player_page);

        if (user == null) {
            ivFollowIcon.setVisibility(View.GONE);
        }

        tvPlayerName.setText(player.getPlayerName());
        tvPlayerName.setTextColor(Color.parseColor(player.getColorStart()));
        Shader textShader = new LinearGradient(0, 0, tvPlayerName.getPaint().measureText(tvPlayerName.getText().toString()), tvPlayerName.getTextSize(),
                new int[]{Color.parseColor(player.getColorStart()), Color.parseColor(player.getColorEnd())},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tvPlayerName.getPaint().setShader(textShader);

        if (player.getTwitchAcc() != null) {
            tvTwitchLink.setText("Twitch: " + player.getTwitchAcc());
        }

        if (player.getYoutubeAcc() != null) {
            tvYoutubeLink.setText("Youtube: " + player.getYoutubeAcc());
        }

        tvWebLink.setText("View profile on the official website");
        tvLocation.setText(player.getCountryName());


        Glide.with(this)
                .load("https://www.speedrun.com/userasset/" + playerId + "/image?v=50b337a")
                .override(300, 200)
                .into(ivProfilePic);

    }

    public void openTwitchLink(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(player.getTwitchAcc()));
        startActivity(browserIntent);
    }

    public void openYoutubeLink(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(player.getYoutubeAcc()));
        startActivity(browserIntent);
    }

    public void openWebLink(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(player.getWebLink()));
        startActivity(browserIntent);
    }

    public void followPlayer(View v) throws IOException {
        user.addFollowedPlayer(player);
        ArrayList<User> userArrayList = rJson.getUserList(this);
        for (int i = 0 ; i < userArrayList.size() ; i++) {
            if (user.getUsername().compareTo(userArrayList.get(i).getUsername()) == 0) {
                userArrayList.remove(i);
            }
        }
        userArrayList.add(user);
        wJson.writeJsonStream(this, userArrayList);

        ivUnFollowIcon.setVisibility(View.VISIBLE);
        ivFollowIcon.setVisibility(View.GONE);
    }

    public void unFollowPlayer(View v) throws IOException {
        ArrayList<Player> userFollowedPlayers = user.getFollowedPlayers();
        for (int i = 0 ; i < userFollowedPlayers.size() ; i++) {
            if (player.getPlayerId().compareTo(userFollowedPlayers.get(i).getPlayerId()) == 0) {
                userFollowedPlayers.remove(i);
                user.setFollowedPlayers(userFollowedPlayers);
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

        ivUnFollowIcon.setVisibility(View.GONE);
        ivFollowIcon.setVisibility(View.VISIBLE);
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
