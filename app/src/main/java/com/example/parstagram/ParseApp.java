package com.example.parstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("isabellalr")
                .clientKey("isabella21")
                .server("http://parstagram21.herokuapp.com/parse")
                .build();

        Parse.initialize(configuration);
    }

}
