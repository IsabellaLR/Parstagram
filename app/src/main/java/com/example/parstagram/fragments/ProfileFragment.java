package com.example.parstagram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.ProfileAdapter;
import com.example.parstagram.R;
import com.example.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment{

    public String photoFileName = "photo.jpg";
    private File photoFile;
    private ImageView ivPostImage;
    private static final String imagePath = "icon.png";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1035;
    public CircleImageView profile;
    public Button logOut;
    protected RecyclerView rvProfilePosts;
    protected ProfileAdapter adapter;
    protected List<Post> mPosts;
    public static final String TAG = "ProfileFragment";


    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        Button changePic = (Button) view.findViewById(R.id.changePic);
        Button logOut = (Button) view.findViewById(R.id.btn_log);
//        posts = new ArrayList<>();
//        postAdapter = new PostsAdapter(posts);
        profile = view.findViewById(R.id.profile_image);
        ParseFile image = ParseUser.getCurrentUser().getParseFile("profile");
        if (image != null) {
            Glide.with(getContext()).load(image.getUrl()).into(profile);
        }
//        rvPosts = view.findViewById(R.id.rvPost);
//        Glide.with(getActivity()).load()

        // Setup any handles to view objects here
        rvProfilePosts = view.findViewById(R.id.rvProfilePosts);

        //create the data source
        mPosts = new ArrayList<>();
        //create the adapter
        adapter = new ProfileAdapter(getContext(), mPosts);
        //set the adapter on the recycler view
        rvProfilePosts.setAdapter(adapter);
        //set the the layout manager on the recycler view
        rvProfilePosts.setLayoutManager(new GridLayoutManager(getContext(), 3));

        queryPosts();

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                launchCamera();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ParseUser.logOut();
            }
        });

        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getContext(), 3);
        // Attach the layout manager to the recycler view
        rvProfilePosts.setLayoutManager(gridLayoutManager);
        rvProfilePosts.setAdapter(adapter);
    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_DATE);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();
//                swipeContainer.setRefreshing(false);
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }
            }
        });
    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
//                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
//                ivPostImage.setImageBitmap(takenImage);
                Toast.makeText(getContext(), "Picture taken!", Toast.LENGTH_SHORT).show();
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                profile.setImageBitmap(takenImage);

                //write to database
                ParseUser user = ParseUser.getCurrentUser();
                user.put("profile", new ParseFile(photoFile));
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

