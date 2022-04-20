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
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {

    ReadJSON rJson = ReadJSON.getInstance();
    WriteJSON wJson = WriteJSON.getInstance();

    private EditText usernameLogin;
    private EditText usernameSignUp;
    private EditText passwordLogin;
    private EditText passwordSignUp;
    private TextView falseLogin;

    private String usernameInput;
    private String passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        usernameLogin = findViewById(R.id.et_login_username_login_page);
        usernameSignUp = findViewById(R.id.et_signup_username_login_page);
        passwordLogin = findViewById(R.id.et_login_password_login_page);
        passwordSignUp = findViewById(R.id.et_signup_password_login_page);
        falseLogin = findViewById(R.id.tv_false_login_page);

        falseLogin.setVisibility(View.GONE);

    }

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

    public void login (View v) {
        usernameInput = usernameLogin.getText().toString();
        passwordInput = passwordLogin.getText().toString();

        ArrayList<User> userArrayList = rJson.getUserList(this.getApplicationContext());
        System.out.println(userArrayList);
        for (User user : userArrayList) {
            if (usernameInput.compareTo(user.getUsername()) == 0) {
                // Storing the current user
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("current_user.txt", Context.MODE_PRIVATE));
                    outputStreamWriter.write(user.getUsername());
                    outputStreamWriter.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginPage.this, MainActivity.class);
                intent.putExtra("current_user", user.getUsername());
                startActivity(intent);
            }
        }
        falseLogin.setVisibility(View.VISIBLE);

    }

    public void signUp (View v) {
        usernameInput = usernameSignUp.getText().toString();
        passwordInput = passwordSignUp.getText().toString();

        ArrayList<User> userArrayList = rJson.getUserList(this.getApplicationContext());
        User newUser = new User(usernameInput, passwordInput);
        userArrayList.add(newUser);

        File userDataFile = new File(getApplicationContext().getFilesDir(), "user_data");
        userDataFile = new File(this.getFilesDir(), "user_data");

        try {
            wJson.writeJsonStream(new FileOutputStream(userDataFile), userArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Storing the current user
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.openFileOutput("current_user.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(newUser.getUsername());
            outputStreamWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(LoginPage.this, MainActivity.class);
        intent.putExtra("current_user", newUser.getUsername());
        startActivity(intent);

    }

}
