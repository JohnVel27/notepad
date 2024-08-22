package com.llacuna.johnvelnotepad;

import static java.util.Locale.filter;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.llacuna.johnvelnotepad.adapters.notelistadapters;
import com.llacuna.johnvelnotepad.adapters.notesclicklistener;
import com.llacuna.johnvelnotepad.database.roomdb;
import com.llacuna.johnvelnotepad.models.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class main2 extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    notelistadapters noteslistadapter;
    List<notes> note = new ArrayList<>();
    roomdb database;
    FloatingActionButton fab_add;
    SearchView searchview_home;
    notes selectednotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recycleviewhome);
        fab_add = findViewById(R.id.fab_add);
        searchview_home = findViewById(R.id.searchview_home);

        database = roomdb.getInstance(this);
        note = database.mainDao().getAll();

        updateRecycler(note);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main2.this,notestakeractivity.class);
                startActivityForResult(intent,101);
            }
        });

        searchview_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String newText) {
        List<notes> filteredlist = new ArrayList<>();
        for(notes singlenote: note){
            if(singlenote.getTitle().toLowerCase().contains(newText.toLowerCase(Locale.ROOT)) || singlenote.getTextnotes().toLowerCase().contains(newText.toLowerCase())){
                filteredlist.add(singlenote);
            }
        }
        noteslistadapter.filterlist(filteredlist);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                notes new_notes = (notes) data.getSerializableExtra("note");
                database.mainDao().insert(new_notes);
                note.clear();
                note.addAll(database.mainDao().getAll());
                noteslistadapter.notifyDataSetChanged();
            }
        }
        else if(requestCode==102){
            if(resultCode==Activity.RESULT_OK){
                notes new_notes = (notes) data.getSerializableExtra("note");
                database.mainDao().update(new_notes.getId(),new_notes.getTitle(),new_notes.getTextnotes());
                note.clear();
                note.addAll(database.mainDao().getAll());
                noteslistadapter.notifyDataSetChanged();

            }
        }
    }

    private void updateRecycler(List<notes> note) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        noteslistadapter = new notelistadapters(main2.this,note,noteclicklistener);
        recyclerView.setAdapter(noteslistadapter);
    }
    private final notesclicklistener noteclicklistener = new notesclicklistener() {
        @Override
        public void onClick(notes note) {
                Intent intent = new Intent(main2.this,notestakeractivity.class);
                intent.putExtra("old_note",note);
                startActivityForResult(intent,102);
        }

        @Override
        public void onLongClick(notes note, CardView cardView) {
                selectednotes = new notes();
                selectednotes = note;
                showPopup(cardView);
        }
    };

    private void showPopup(CardView cardView) {

        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.pin) {
            if (selectednotes.isPinned()) {
                database.mainDao().pin(selectednotes.getId(), false);
                Toast.makeText(main2.this, "Unpinned", Toast.LENGTH_SHORT).show();
            } else {
                database.mainDao().pin(selectednotes.getId(), true);
                Toast.makeText(main2.this, "Pinned", Toast.LENGTH_SHORT).show();
            }

            note.clear();
            note.addAll(database.mainDao().getAll());
            noteslistadapter.notifyDataSetChanged();
            return true;

        } else if (item.getItemId() == R.id.delete) {
            database.mainDao().delete(selectednotes);
            note.remove(selectednotes);
            noteslistadapter.notifyDataSetChanged();
            Toast.makeText(main2.this, "Note Deleted!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }


}