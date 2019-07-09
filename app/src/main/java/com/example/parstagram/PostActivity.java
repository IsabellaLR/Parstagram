package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button shareButton = (Button) findViewById(R.id.btn_share);
        ImageView imageCapture = (ImageView) findViewById(R.id.imageCapture);
        final EditText description = (EditText) findViewById(R.id.description);

        final File image = (File) getIntent().getExtras().get("image");
        Bitmap takenImage = BitmapFactory.decodeFile(image.getAbsolutePath());
        imageCapture.setImageBitmap(takenImage);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = description.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

//                if (parseFile image == null) {
//                    Toast.makeText(PostActivity.this, "No photo", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                Toast.makeText(PostActivity.this, "Check", Toast.LENGTH_SHORT).show();
                createPost(text, image, user);
            }
        });
    }

    private void createPost(String description, File file, ParseUser user) {
        final Post newPost = new Post();
//        ParseFile newFile = new ParseFile(file);
        newPost.setDescription(description);
        newPost.setImage(new ParseFile(file));
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success!");
                    finish();
                } else {
                    Toast.makeText(PostActivity.this, "NO SUCCESS", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
