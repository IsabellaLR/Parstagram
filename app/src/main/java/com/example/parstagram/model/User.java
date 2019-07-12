package com.example.parstagram.model;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_PROFILE = "profile";

    public ParseFile getProfile(){
        return getParseFile(KEY_PROFILE);
    }

    public void setProfile(ParseFile profile) {
        put(KEY_PROFILE, profile);
    }

//    public static class Query extends ParseQuery<Post> {
//        public Query() {
//            super(Post.class);
//        }
//
//        public Query getTop() {
//            setLimit(20);
//            return this;
//        }
//
//        public Query withUser() {
//            include("user");
//            return this;
//        }
//    }
}
