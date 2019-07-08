package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignActivity extends AppCompatActivity {

    private Button signUpButton;
    private EditText signUpEmail;
    private EditText signUpUsername;
    private EditText signUpPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        signUpButton = findViewById(R.id.signUp_btn);
        signUpEmail = findViewById(R.id.sign_email);
        signUpUsername = findViewById(R.id.sign_username);
        signUpPassword = findViewById(R.id.sign_password);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the ParseUser
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(signUpUsername.getText().toString());
                user.setPassword(signUpPassword.getText().toString());
                user.setEmail(signUpEmail.getText().toString());
//                // Set custom properties
//                user.put("phone", "650-253-0000");
                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                            Log.e("SignActivity", "Sign up success!");
                            e.printStackTrace();
                            finish();
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Log.e("SignActivity", "Sign up failed");
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}



