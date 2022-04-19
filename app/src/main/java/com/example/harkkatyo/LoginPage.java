package com.example.harkkatyo;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    private EditText usernameLogin;
    private EditText usernameSignUp;
    private EditText passwordLogin;
    private EditText passwordSignUp;

    private String usernameInput;
    private String passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        usernameLogin = findViewById(R.id.tv_username_login_);
        usernameSignUp = findViewById(R.id.tv_username_sign_up);
        passwordLogin = findViewById(R.id.tv_password_login);
        passwordSignUp = findViewById(R.id.tv_password_sign_up);

    }

    public void login (View v) {
        usernameInput = usernameLogin.getText().toString();
        passwordInput = passwordLogin.getText().toString();
    }

    public void signUp (View v) {
        usernameInput = usernameSignUp.getText().toString();
        passwordInput = passwordSignUp.getText().toString();
    }

}
