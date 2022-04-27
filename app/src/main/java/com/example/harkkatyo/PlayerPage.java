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

public class PlayerPage extends AppCompatActivity {

    ReadJSON json = ReadJSON.getInstance();

    private Player player;

    private ImageView ivFlag;
    private TextView tvPlayerName;
    private TextView tvWebLink;
    private TextView tvYoutubeLink;
    private TextView tvTwitchLink;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String playerId = intent.getStringExtra("playerId");
        player = json.getPlayerData(playerId);

        tvPlayerName = findViewById(R.id.tv_player_name_player_page);
        tvWebLink = findViewById(R.id.tv_player_weblink_player_page);
        tvYoutubeLink = findViewById(R.id.tv_player_youtubelink_player_page);
        tvTwitchLink = findViewById(R.id.tv_player_twitch_player_page);
        ivFlag = findViewById(R.id.iv_cover_image_player_page);

        tvPlayerName.setText(player.getPlayerName());
        tvPlayerName.setTextColor(Color.parseColor(player.getColorStart()));
        Shader textShader = new LinearGradient(0, 0, tvPlayerName.getPaint().measureText(tvPlayerName.getText().toString()), tvPlayerName.getTextSize(),
                new int[]{Color.parseColor(player.getColorStart()), Color.parseColor(player.getColorEnd())},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        tvPlayerName.getPaint().setShader(textShader);

        if (player.getTwitchAcc() != null) {
<<<<<<< Updated upstream
            tvTwitchLink.setText("Twitchlink: " + player.getTwitchApp());
        }

        if (player.getYoutubeAcc() != null) {
            tvYoutubeLink.setText("YoutubeLink: " + player.getYoutubeAcc());
=======
            tvTwitchLink.setText("View profile on Twitch");
        }

        if (player.getYoutubeAcc() != null) {
            tvYoutubeLink.setText("View profile on Youtube");
>>>>>>> Stashed changes
        }

        tvWebLink.setText("WebLink: " + player.getWebLink());

        Glide.with(this)
                .load(player.getCountryCode())
                .override(300, 200)
                .into(ivFlag);

    }

    public void openTwitchLink(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(player.getTwitchAcc());
        startActivity(browserIntent);
    }

    public void openYoutubeLink(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(player.getYoutubeAcc());
        startActivity(browserIntent);
    }

    public void openWebLink(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(player.getWebLink());
        startActivity(browserIntent);
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
