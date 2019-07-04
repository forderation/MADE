package com.example.submission2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    static final String PATH_IMG = "https://image.tmdb.org/t/p/w500";

    String getOriginalTitle() {
        return originalTitle;
    }

    void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    String getOverview() {
        return overview;
    }

    void setOverview(String overview) {
        this.overview = overview;
    }

    String getPosterPath() {
        return posterPath;
    }

    void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    String getVoteAverage() {
        return voteAverage;
    }

    void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    String getReleaseDate() {
        return releaseDate;
    }

    void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    String getHomepage() {
        return homepage;
    }

    void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    String getBackdropPath() {
        return backdropPath;
    }

    void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    String getAdult() {
        return adult;
    }

    void setAdult(String adult) {
        this.adult = adult;
    }

    String getTagline() {
        return tagline;
    }

    void setTagline(String tagline) {
        this.tagline = tagline;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    String getOriginalLanguage() {
        return originalLanguage;
    }

    void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    private String originalTitle, overview, posterPath, voteAverage, releaseDate, homepage, backdropPath, adult, tagline, status, originalLanguage;

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
        dest.writeString(this.originalLanguage);
    }

    Movie() {
    }

    private Movie(Parcel in) {
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
        this.originalLanguage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
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
