package com.floooh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Search extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter = new Adapter();
    private Cloud mCloud = new Cloud();
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRecyclerView = findViewById(R.id.recyclerview);
        mButton = findViewById(R.id.search_btn);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSerach();
            }
        });
    }

    private void onSerach() {
        String query = ((EditText) findViewById(R.id.query_et)).getText().toString();
        mCloud.fetchMovies(Constants.Urls.SEARCH + query, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(Search.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                });
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String res = response.body().string();
                    Movie m = Movie.from(res);
                    if (m != null) {
                        runOnUiThread(() -> {
                            ArrayList<Movie> list = new ArrayList<>();
                            list.add(m);
                            mAdapter.setMovies(list);
                        });
                    }else{
                        runOnUiThread(()->{
                            Toast.makeText(Search.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
