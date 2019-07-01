package com.example.submission2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final String PATH_IMG = "https://image.tmdb.org/t/p/w300";
    private String originalTitle, overview, posterPath, voteAverage, releaseDate, homepage, backdropPath, adult, tagline, status;

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteAverage);
        dest.writeString(this.releaseDate);
        dest.writeString(this.homepage);
        dest.writeString(this.backdropPath);
        dest.writeString(this.adult);
        dest.writeString(this.tagline);
        dest.writeString(this.status);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.voteAverage = in.readString();
        this.releaseDate = in.readString();
        this.homepage = in.readString();
        this.backdropPath = in.readString();
        this.adult = in.readString();
        this.tagline = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
