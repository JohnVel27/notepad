package com.llacuna.johnvelnotepad.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.llacuna.johnvelnotepad.models.notes;

import java.util.List;

@Dao
public interface maindao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(notes note);

    @Query("SELECT * FROM notes ORDER BY Id DESC")
    List<notes> getAll();

    @Query("UPDATE notes SET title = :title, textnotes = :textnotes WHERE Id = :id")
    void update(int id, String title, String textnotes);

    @Delete
    void delete(notes note);

    @Query("Update notes SET pinned = :pin where Id=:id")
    void pin(int id,boolean pin);
}



