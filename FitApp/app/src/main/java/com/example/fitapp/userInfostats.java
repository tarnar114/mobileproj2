package com.example.fitapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.fitapp.SQLHelperUser;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class userInfostats extends MainActivity{
    RecyclerView recyclerview;
    SQLHelperUser db;
    useradapter adapter;
    EditText weight;
    FloatingActionButton button;
    ArrayList<String> dates;
    ArrayList<Integer> weights;
    String weightVal;
    private Calendar cal;
    private SimpleDateFormat format;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userupdates);

        recyclerview=findViewById(R.id.users);
        db=new SQLHelperUser(userInfostats.this);
        dates=new ArrayList<>();
        weights=new ArrayList<>();
        displayData();



        button=findViewById(R.id.addWeight);
        button.setOnClickListener(v->addWeight());
        adapter=new useradapter(userInfostats.this,dates,weights);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(userInfostats.this));
        Log.i("data items",String.valueOf(adapter.getItemCount()));
    }
    void addWeight(){
        weight=findViewById(R.id.input);
        Toast.makeText(userInfostats.this,weight.getText().toString(),Toast.LENGTH_SHORT).show();

        if (!weight.getText().toString().isEmpty()){
            weightVal=weight.getText().toString();
            cal=Calendar.getInstance();
            format=new SimpleDateFormat("MM/dd/yyyy");
            date=format.format(cal.getTime());
            db.addUser(date,Integer.parseInt(String.valueOf(weight.getText())));
            Log.i("weights",String.valueOf(weights.size()));
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(userInfostats.this,"enter number",Toast.LENGTH_SHORT);
        }
    }
    void displayData(){
        Cursor cursor=db.readAll();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                dates.add(cursor.getString(1));
                weights.add(cursor.getInt(2));
                Log.i("dates",dates.toString());
                Log.i("weight",weights.toString());
            }
        }
    }


}
