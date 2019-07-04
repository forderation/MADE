package com.example.submission2;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShow implements Parcelable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getVoteAverage() {
        return voteAverage;
    }

    void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    String getBackdropPath() {
        return backdropPath;
    }

    void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    String getPosterPath() {
        return posterPath;
    }

    void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    String getOverview() {
        return overview;
    }

    void setOverview(String overview) {
        this.overview = overview;
    }

    String getOriginalLanguage() {
        return originalLanguage;
    }

    void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    String getOriginalName() {
        return originalName;
    }

    void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    String getFirstAirDate() {
        return firstAirDate;
    }

    void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    private String name, voteAverage, backdropPath, posterPath, overview, originalLanguage, originalName, firstAirDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.voteAverage);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalName);
        dest.writeString(this.firstAirDate);
    }

    TvShow() {
    }

    private TvShow(Parcel in) {
        this.name = in.readString();
        this.voteAverage = in.readString();
        this.backdropPath = in.readString();
        this.posterPath = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.originalName = in.readString();
        this.firstAirDate = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
