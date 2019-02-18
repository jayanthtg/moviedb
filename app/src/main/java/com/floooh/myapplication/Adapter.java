package com.floooh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private ArrayList<Movie> movies = new ArrayList<>();
    private DatabaseHandler mDatabaseHandler;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mDatabaseHandler = new DatabaseHandler(viewGroup.getContext());
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.item_movie, viewGroup, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        Movie m = movies.get(i);
        Picasso.get().load(m.getmPoster()).into(holder.poster);
        holder.title.setText(m.getmTitle());
        holder.date.setText(m.getmDate());
        holder.container.setTag(m);
        if (m.isLiked()) {
            holder.mlikeBtn.setText("unlike");
        } else {
            holder.mlikeBtn.setText("like");
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    private void fetchMovies() {
        this.movies = mDatabaseHandler.getMovies();
        notifyDataSetChanged();
    }


    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        TextView title, date;
        Button mlikeBtn;
        View container;

        public Holder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster_iv);
            title = itemView.findViewById(R.id.title_tv);
            date = itemView.findViewById(R.id.date_tv);
            mlikeBtn = itemView.findViewById(R.id.like_btn);
            container = itemView.findViewById(R.id.container);
            container.setOnClickListener(this);
            mlikeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.like_btn: {
                    Movie m = (Movie) ((View) v.getParent()).getTag();
                    if (m != null) {
                        if (((Button) v).getText().toString().toLowerCase().equals("unlike")) {
                            mDatabaseHandler.deleteMovie(m.getmTitle());
                            ((Button) v).setText("Like");
                        } else {
                            mDatabaseHandler.addMovie(m);
                            ((Button) v).setText("UnLike");
                        }

                    }
                    break;
                }
                case R.id.container: {
                    Intent intent = new Intent(v.getContext(), DetailActiviy.class);
                    intent.putExtra("data", (Movie) v.getTag());
                    v.getContext().startActivity(intent);
                }
            }
        }
    }
}
