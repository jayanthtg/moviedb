package com.floooh.myapplication;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Cloud {

    private OkHttpClient client = new OkHttpClient();

    public void fetchMovies(String url, Callback callback) {
        try {
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
