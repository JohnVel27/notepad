package com.llacuna.johnvelnotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.llacuna.johnvelnotepad.models.notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class notestakeractivity extends AppCompatActivity {

    EditText edittext_title,editText_notes;
    ImageView imageview_save;
    notes note;
    boolean isOldnote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notestakeractivity);

        imageview_save = findViewById(R.id.imageview_save);
        edittext_title = findViewById(R.id.edittext_title);
        editText_notes = findViewById(R.id.editText_notes);

        note = new notes();
        try{
            note = (notes) getIntent().getSerializableExtra("old_note");
            edittext_title.setText(note.getTitle());
            editText_notes.setText(note.getTextnotes());
            isOldnote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        imageview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edittext_title.getText().toString();
                String description = editText_notes.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(notestakeractivity.this,"Please add some notes!",Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if(!isOldnote){
                    note = new notes();
                }

                note.setTitle(title);
                note.setTextnotes(description);
                note.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",note);

                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}