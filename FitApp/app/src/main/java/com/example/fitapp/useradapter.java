package com.example.fitapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class useradapter extends RecyclerView.Adapter<useradapter.viewHolder>{
    private Context context;
    private ArrayList<Integer> weights;
    private ArrayList<String> dates;

    public useradapter(Context context, ArrayList<String> dates,ArrayList<Integer> weights) {
        this.context = context;
        this.dates = dates;
        this.weights=weights;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView weight,date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            weight=itemView.findViewById(R.id.weight_view);
            date=itemView.findViewById(R.id.time_view);
        }
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.userinfo,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.date.setText(dates.get(position));
        holder.weight.setText(String.valueOf(weights.get(position))+" ibs");
    }

    @Override
    public int getItemCount() {
        return weights.size();
    }
}
