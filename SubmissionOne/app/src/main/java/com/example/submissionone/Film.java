package com.example.submissionone;

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {
    static final String URI_IMG = "https://image.tmdb.org/t/p/w300";

    private String title, overview, poster, vote, release, homepage, background;

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getOverview() {
        return overview;
    }

    void setOverview(String overview) {
        this.overview = overview;
    }

    String getPoster() {
        return poster;
    }

    void setPoster(String poster) {
        this.poster = poster;
    }

    String getVote() {
        return vote;
    }

    void setVote(String vote) {
        this.vote = vote;
    }

    String getRelease() {
        return release;
    }

    void setRelease(String release) {
        this.release = release;
    }

    String getHomepage() {
        return homepage;
    }

    void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    String getBackground() {
        return background;
    }

    void setBackground(String background) {
        this.background = background;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.vote);
        dest.writeString(this.release);
        dest.writeString(this.homepage);
        dest.writeString(this.background);
    }

    Film() {
    }

    private Film(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.vote = in.readString();
        this.release = in.readString();
        this.homepage = in.readString();
        this.background = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
}
