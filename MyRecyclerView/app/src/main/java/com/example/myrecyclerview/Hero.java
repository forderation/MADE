package com.example.myrecyclerview;

import android.os.Parcel;
import android.os.Parcelable;

public class Hero implements Parcelable {
    private String name;
    private String from;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getFrom() {
        return from;
    }

    void setFrom(String from) {
        this.from = from;
    }

    String getPhoto() {
        return photo;
    }

    void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.from);
        dest.writeString(this.photo);
    }

    public Hero() {
    }

    protected Hero(Parcel in) {
        this.name = in.readString();
        this.from = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<Hero> CREATOR = new Parcelable.Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };
}
