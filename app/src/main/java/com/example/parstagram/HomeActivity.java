package com.example.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.parstagram.fragments.HomeFragment;
import com.example.parstagram.fragments.PhotoFragment;
import com.example.parstagram.fragments.ProfileFragment;
import com.example.parstagram.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "icon.png";
    private EditText descriptionInput;
    private Button createButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new PhotoFragment();
        final Fragment fragment3 = new ProfileFragment();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        descriptionInput = findViewById(R.id.description_et);
//        createButton = findViewById(R.id.create_btn);
//        refreshButton = findViewById(R.id.refresh_btn);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_photo:
                        fragment = new PhotoFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.f1Container, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);

//        createButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                final String description = descriptionInput.getText().toString();
//                final ParseUser user = ParseUser.getCurrentUser();
//
//                final File file = new File(imagePath);
//                final ParseFile parseFile = new ParseFile(file);
//
//                createPost(description, parseFile, user);
//            }
//        });
//
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//                loadTopPosts();
//            }
//        });
    }

    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("HomeActivity", "Create post success!");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();


        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e){
                if (e ==  null){
                    for (int i = 0; i <objects.size(); ++i) {
                        Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i).getDescription()
                                + "\nusername = " + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
