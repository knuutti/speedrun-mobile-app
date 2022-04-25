package com.example.harkkatyo;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.bumptech.glide.Glide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    WriteJSON wJson = WriteJSON.getInstance();
    ReadJSON rJson = ReadJSON.getInstance();

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setUserData();
        currentUser = rJson.getCurrentUser(this);
    }

    public void setUserData(){
        File userDataFile = new File(getApplicationContext().getFilesDir(), "user_data");

        if (userDataFile.exists()) {
            System.out.println("User data loaded");
        }
        else {
            System.out.println("No user data, creating a file");

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
        MenuItem login = menu.findItem(R.id.login);
        if (currentUser != null) {
            login.setTitle(currentUser.getUsername());
        }
        else {
            login.setTitle("LOGIN");
        }
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

}