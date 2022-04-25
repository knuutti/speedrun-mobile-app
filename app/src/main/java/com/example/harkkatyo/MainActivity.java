package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import com.bumptech.glide.Glide;
import java.io.BufferedWriter;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WriteJSON wJson = WriteJSON.getInstance();
    //WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setUserData();

    }

    public void setUserData(){
        File userDataFile = new File(getApplicationContext().getFilesDir(), "user_data");

        if (userDataFile.exists()) {
            System.out.println("File exists");
        }
        else {
            System.out.println("User data does not exists, creating a file");

            // Creating a JSON file for storing all of the users
            userDataFile = new File(this.getApplicationContext().getFilesDir(), "user_data.json");
            ArrayList<User> users = new ArrayList<>();

            try {
                wJson.writeJsonStream(new FileOutputStream(userDataFile), users);
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

    public void goToGameSearchPage(View v) {
        Intent intent = new Intent(MainActivity.this, GameSearchPage.class);
        startActivity(intent);
    }

    public void goToLoginPage(View v) {
        Intent intent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(intent);
    }

    public void goToPlayerSearchPage(View v) {
        Intent intent = new Intent(MainActivity.this, PlayerSearchPage.class);
        startActivity(intent);
    }

    // TODO tää ei jostain syystä toimi, en keksinyt mitään syytä mut voi koittaa :-(
   /*public void imageWeb(View v) {
        web = findViewById(R.id.webView);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl("http://www.youtube.com");
    }*/

}