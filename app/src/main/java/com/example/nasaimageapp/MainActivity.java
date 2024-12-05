package com.example.nasaimageapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fetchImageButton = findViewById(R.id.fetchImageButton);
        Button savedImagesButton = findViewById(R.id.savedImagesButton);

        fetchImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FetchImageActivity.class);
            startActivity(intent);
        });

        savedImagesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SavedImagesActivity.class);
            startActivity(intent);
        });
    }
}
