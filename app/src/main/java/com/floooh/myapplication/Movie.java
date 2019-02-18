package com.floooh.myapplication;

import org.json.JSONObject;

import java.io.Serializable;

public class Movie implements Serializable {
    private String mTitle, mDate, mOverview, mGener, mRuntime, mPoster, mRating;
    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Movie(String mTitle, String mDate, String mOverview, String mGener, String mRuntime, String mPoster, String mRating) {
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mOverview = mOverview;
        this.mGener = mGener;
        this.mRuntime = mRuntime;
        this.mPoster = mPoster;
        this.mRating = mRating;
        this.liked = false;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmGener() {
        return mGener;
    }

    public void setmGener(String mGener) {
        this.mGener = mGener;
    }

    public String getmRuntime() {
        return mRuntime;
    }

    public void setmRuntime(String mRuntime) {
        this.mRuntime = mRuntime;
    }

    public String getmPoster() {
        return mPoster;
    }

    public void setmPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getmRating() {
        return mRating;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public static Movie from(String res) {
        try {
            JSONObject o = new JSONObject(res);
            String title = o.getString("Title");
            String date = o.getString("Released");
            String time = o.getString("Runtime");
            String gen = o.getString("Genre");
            String plot = o.getString("Plot");
            String pos = o.getString("Poster");
            String rating = o.getString("imdbRating");
            return new Movie(title, date, plot, gen, time, pos, rating);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
