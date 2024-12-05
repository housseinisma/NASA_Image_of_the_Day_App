package com.example.nasaimageapp;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SavedImagesActivity extends AppCompatActivity {

    private ArrayList<String> savedImages;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        ListView listView = findViewById(R.id.listView);

        // Load saved images (mocked data for now)
        savedImages = loadSavedImages();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, savedImages);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((AdapterView<?> parent, android.view.View view, int position, long id) -> {
            String selectedItem = savedImages.get(position);
            Toast.makeText(SavedImagesActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String itemToRemove = savedImages.get(position);
            savedImages.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(SavedImagesActivity.this, "Deleted: " + itemToRemove, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private ArrayList<String> loadSavedImages() {
        // Mock data (replace with database or shared preferences later)
        ArrayList<String> images = new ArrayList<>();
        images.add("NASA Image - 2023-12-01");
        images.add("NASA Image - 2023-12-02");
        return images;
    }
}
