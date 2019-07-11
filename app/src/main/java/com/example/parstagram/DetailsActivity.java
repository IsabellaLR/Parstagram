package com.example.parstagram;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {

    TextView description;
    TextView username;
    ImageView image;
    TextView date;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_home);
//        getSupportActionBar().setTitle("Tweet");

        //1 - pass data back
        description = (TextView) findViewById(R.id.d_description);
        username = (TextView) findViewById(R.id.username);
        image = (ImageView) findViewById(R.id.d_image);
        date = (TextView) findViewById(R.id.d_date);
        profile = (ImageView) findViewById(R.id.d_profile);

        description.setText(getIntent().getStringExtra("description"));
        username.setText(getIntent().getStringExtra("user"));
        date.setText(getIntent().getStringExtra("date"));
        Glide.with(this).load(getIntent().getStringExtra("profile")).into(profile);
        Glide.with(this).load(getIntent().getStringExtra("image")).into(image);

    }

    public void exit(View v) {
        finish();
    }
}
