package com.example.fitapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "user")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="weight")
    public int weight;

    @ColumnInfo(name="date")
    public Date date;
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
