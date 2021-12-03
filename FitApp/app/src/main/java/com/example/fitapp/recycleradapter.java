package com.example.fitapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitapp.Song;
import java.io.File;
import java.util.ArrayList;

public class recycleradapter extends RecyclerView.Adapter<recycleradapter.MyViewHolder> {
    private Context context;
    private ArrayList<Song> displaySongs;
    private ArrayList<Song> allSongs;

    public recycleradapter(@NonNull Context context, ArrayList<Song> availSongs) {
        this.context=context;
        this.displaySongs=availSongs;
        this.allSongs=new ArrayList<>(displaySongs);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.song_name_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.songName.setText(displaySongs.get(position).getSongName());
        Intent intent=new Intent(holder.itemView.getContext(),SongPlayer.class);
        intent.putExtra("songName",displaySongs.get(position).getSongName());
        intent.putExtra("position",position);
        intent.putExtra("song_list",allSongs);
        holder.cardView.setOnClickListener(v ->holder.itemView.getContext().startActivity(intent));

    }

    @Override
    public int getItemCount() {
        return displaySongs.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView songName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.SongName);
            cardView=itemView.findViewById(R.id.songCard);
        }
    }
}
