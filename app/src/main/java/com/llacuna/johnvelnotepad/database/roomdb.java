package com.llacuna.johnvelnotepad.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.llacuna.johnvelnotepad.models.notes;

@Database(entities = {notes.class}, version = 1, exportSchema = false)
public abstract class roomdb extends RoomDatabase {

    private static roomdb instance;
    private static final String DATABASE_NAME = "noteapp";

    public static synchronized roomdb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            roomdb.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract maindao mainDao();
}

