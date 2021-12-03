package com.example.fitapp;

import android.Manifest.permission;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.example.fitapp.recycleradapter;
import com.example.fitapp.Song;
import java.io.File;
import java.util.ArrayList;

public class songActivity extends MainActivity {
    String[] items;
    RecyclerView recycleradapter;
    ArrayList<Song> availSongs;
    recycleradapter songadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);

        recycleradapter =findViewById(R.id.listViewSong);

        availSongs=new ArrayList<>();
        runtimePerm();



    }
    public void runtimePerm(){
        Dexter.withContext(getApplicationContext()).withPermission(permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {

                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }
    public ArrayList<Song> findSong(File file){
        ArrayList<Song> arrayList=new ArrayList<>();
        File[] files=file.listFiles();
        if (files!=null){
            for (int i=0;i<files.length;i++){
                if (files[i].isDirectory() && !files[i].isHidden()){
                    arrayList.addAll(findSong(files[i]));
                    Log.i("file",files[i].getPath());
                }
                else {
                    if (files[i].getName().endsWith(".mp3")||files[i].getName().endsWith(".wav")){
                        Log.i("file",files[i].getName());
                        Song song=new Song(String.valueOf(i),files[i].getName(),files[i].getPath());
                        arrayList.add(song);

                    }
                }
            }

        }


        return arrayList;

    }

    void displaySongs(){
        final ArrayList<Song> mysongs=findSong(Environment.getExternalStorageDirectory());
        Log.i("songs",mysongs.toString());
        items=new String[mysongs.size()];
        for (int i=0;i<mysongs.size();i++){
//            items[i]=mysongs.get(i).toString().replace(".mp3","").replace(".wav","");
                items[i]=mysongs.get(i).getSongName();
        }
        songadapter=new recycleradapter(songActivity.this,mysongs);
        recycleradapter.setAdapter(songadapter);
        recycleradapter.setLayoutManager(new LinearLayoutManager(songActivity.this));


    }


}