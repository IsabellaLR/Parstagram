package com.example.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_USER = "user";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    public static final String KEY_DATE = "createdAt";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_LIKES = "likes";

//    private static final String KEY_USER = "user";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public List <String> getLikes(){
        return getList((KEY_LIKES));
    }

    public void setLikes(List likes) {
        put((KEY_LIKES), likes);
    }

    public String getDate(){
        return getString(KEY_DATE);
    }

    public void setDate(String date) {
        put(KEY_DATE, date);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseFile getProfile(){
        return getParseFile(KEY_PROFILE);
    }

    public void setProfile(ParseFile profile) {
        put(KEY_PROFILE, profile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }
    }
}
