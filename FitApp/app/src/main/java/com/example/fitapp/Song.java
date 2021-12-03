package com.example.fitapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String fileDir;
    private String songName;
    private String id;
    public Song(String id,String songName,String fileDir){
        this.id=id;
        this.fileDir=fileDir;
        this.songName=songName;

    }

    protected Song(Parcel in) {
        fileDir = in.readString();
        songName = in.readString();
        id = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getId(){
        return id;
    }

    public String getFile() {
        return fileDir;
    }

    public String getSongName() {
        return songName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileDir);
        dest.writeString(songName);
        dest.writeString(id);
    }
}
