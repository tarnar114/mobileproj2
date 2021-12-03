package com.example.fitapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SongPlayer extends com.example.fitapp.songActivity {
    Intent intent;
    TextView songName,TxtStart,TxtEnd;
    Button btnPlay, rewind,forward,prevSong,forwardSong;
    SeekBar seekmusic;
    String sname;
    static MediaPlayer player;
    int pos;

    ArrayList<Song> mySongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlay=findViewById(R.id.BtnPlay);
        rewind=findViewById(R.id.BtnFastRewind);
        forward=findViewById(R.id.BtnFastForward);
        prevSong=findViewById(R.id.BtnPrevious);
        forwardSong=findViewById(R.id.BtnNext);

        songName=findViewById(R.id.SongTxt);
        seekmusic=findViewById(R.id.SeekBar);
        TxtStart=findViewById(R.id.TxtSongStart);
        TxtEnd=findViewById(R.id.TxtSongEnd);
        Thread updateSeekbar;
        if(player!=null){
            player.stop();
            player.release();
        }
        intent=getIntent();
        mySongs=intent.getParcelableArrayListExtra("song_list");
        songName.setText(intent.getStringExtra("songName"));
        pos=intent.getIntExtra("position",0);
        Uri uri= Uri.parse(mySongs.get(pos).getFile());
        player=MediaPlayer.create(getApplicationContext(),uri);
        player.start();

//        updateSeekbar=new Thread()
//        {
//            @Override
//            public void run(){
//                int totalDuration=player.getDuration();
//                int currentPos=0;
//
//                while(currentPos<totalDuration){
//                    try {
//                        sleep(200);
//                        currentPos=player.getCurrentPosition();
//                        seekmusic.setProgress(currentPos);
//                    }catch (InterruptedException |IllegalStateException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        seekmusic.setMax(player.getDuration());
//        updateSeekbar.start();


        setActionButtons();
        updateSeekbar=new Thread()
        {
            @Override
            public void run(){
                int totalDuration=player.getDuration();
                int currentPos=0;

                while(currentPos<totalDuration){
                    try {
                        sleep(200);
                        currentPos=player.getCurrentPosition();
                        seekmusic.setProgress(currentPos);
                    }catch (InterruptedException |IllegalStateException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        seekmusic.setMax(player.getDuration());
        updateSeekbar.start();

        String endTime=createTime(player.getDuration());
        TxtEnd.setText(endTime);

        final Handler handler=new Handler();
        final int delay=1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currTime=createTime(player.getCurrentPosition());
                TxtStart.setText(currTime);
                handler.postDelayed(this,delay);

            }
        },delay);


        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                forwardSong.performClick();
            }
        });
        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(seekBar.getProgress());

            }

        });

    }
    protected void setActionButtons(){
        btnPlay.setOnClickListener(v -> {
                    if (player.isPlaying()){
                        btnPlay.setBackgroundResource(R.drawable.play_song_icon);
                        player.pause();
                    }
                    else{
                        btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
                        player.start();
                    }

                }
        );
        forwardSong.setOnClickListener(v->{
            if (pos+1<mySongs.size()){
                pos++;
                player.stop();
                player.release();
                Uri u=Uri.parse(mySongs.get(pos).getFile());
                player=MediaPlayer.create(getApplicationContext(),u);
                songName.setText(mySongs.get(pos).getSongName());
                player.start();
                btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
                seekmusic.setProgress(0);

            }
            else{
                pos=0;
                player.stop();
                player.release();
                Uri u=Uri.parse(mySongs.get(pos).getFile());
                player=MediaPlayer.create(getApplicationContext(),u);
                songName.setText(mySongs.get(pos).getSongName());
                player.start();
                btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
                seekmusic.setProgress(0);
            }

        });
        prevSong.setOnClickListener(v->{
            if (pos==0){
                pos=mySongs.size()-1;
                player.stop();
                player.release();
                Uri u=Uri.parse(mySongs.get(pos).getFile());
                player=MediaPlayer.create(getApplicationContext(),u);
                songName.setText(mySongs.get(pos).getSongName());
                player.start();
                btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
                Log.i("pos",String.valueOf(pos));
                seekmusic.setProgress(0);

            }
            else{
                pos--;
                player.stop();
                player.release();
                Uri u=Uri.parse(mySongs.get(pos).getFile());
                player=MediaPlayer.create(getApplicationContext(),u);
                songName.setText(mySongs.get(pos).getSongName());
                player.start();
                btnPlay.setBackgroundResource(R.drawable.pause_song_icon);
                Log.i("pos",String.valueOf(pos));
                seekmusic.setProgress(0);

            }
        });
        forward.setOnClickListener(v->{
            if(player.isPlaying()){
                player.seekTo(player.getCurrentPosition()+10000);
            }
        });
        rewind.setOnClickListener(v->{
            if(player.isPlaying()){
                player.seekTo(player.getCurrentPosition()-10000);
            }
        });
    }
    protected void initSeekBar(){

    }
    public String createTime(int duration){
        String time="";
        int min=duration/1000/60;
        int sec=duration/1000%60;
        time+=min+":";
        if(sec<10){
            time+="0";

        }
        time+=sec;
        return time;
    }
}
