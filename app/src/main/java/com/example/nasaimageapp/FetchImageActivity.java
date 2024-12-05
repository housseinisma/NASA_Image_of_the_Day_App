package com.example.nasaimageapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchImageActivity extends AppCompatActivity {

    private EditText dateInput;
    private ProgressBar progressBar;
    private ImageView imageView;
    private Button fetchButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_image);

        dateInput = findViewById(R.id.dateInput);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        fetchButton = findViewById(R.id.fetchButton);
        dbHelper = new DatabaseHelper(this);

        fetchButton.setOnClickListener(v -> {
            String date = dateInput.getText().toString();
            if (date.isEmpty()) {
                Toast.makeText(this, "Enter a valid date", Toast.LENGTH_SHORT).show();
            } else {
                new FetchImageTask().execute(date);
            }
        });
    }

    private class FetchImageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(android.view.View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String date = params[0];
            String apiUrl = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + date;
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            progressBar.setVisibility(android.view.View.GONE);
            if (response == null) {
                Toast.makeText(FetchImageActivity.this, "Failed to fetch image", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                String imageUrl = jsonObject.getString("url");
                Picasso.get().load(imageUrl).into(imageView);

                String title = jsonObject.getString("title");
                String date = jsonObject.getString("date");

                // Save to database
                dbHelper.insertImage(title, date);
                Toast.makeText(FetchImageActivity.this, "Image saved!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
