package com.llacuna.johnvelnotepad.adapters;

import androidx.cardview.widget.CardView;

import com.llacuna.johnvelnotepad.models.notes;

public interface notesclicklistener {

    void onClick(notes note);

    void onLongClick(notes note, CardView cardView);
}
