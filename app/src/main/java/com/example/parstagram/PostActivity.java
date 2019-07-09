package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ImageView imageCapture = (ImageView) findViewById(R.id.imageCapture);

        File image = (File)getIntent().getExtras().get("image");
        Bitmap takenImage = BitmapFactory.decodeFile(image.getAbsolutePath());
        imageCapture.setImageBitmap(takenImage);
    }
}
