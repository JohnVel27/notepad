package com.llacuna.johnvelnotepad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the ImageView
        imageView = findViewById(R.id.imagegif);

        // Load the image using Glide
        Glide.with(this)
                .load(R.drawable.notepad) // Load the drawable resource
                .into(imageView); // Set the image into the ImageView


        button = findViewById(R.id.materilbutton1);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, main2.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
