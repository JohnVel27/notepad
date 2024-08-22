package com.llacuna.johnvelnotepad.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class notes implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "textnotes")
    private String textnotes;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "pinned")
    private boolean pinned;

    // Getters and Setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextnotes() {
        return textnotes;
    }

    public void setTextnotes(String textnotes) {
        this.textnotes = textnotes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}

