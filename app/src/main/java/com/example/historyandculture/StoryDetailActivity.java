package com.example.historyandculture;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView detailTitle = findViewById(R.id.detailTitle);
        TextView detailYear = findViewById(R.id.detailYear);
        TextView detailLocation = findViewById(R.id.detailLocation);
        TextView detailDescription = findViewById(R.id.detailDescription);

        // Get data from Intent
        int imageResId = getIntent().getIntExtra("imageResId", 0);
        String title = getIntent().getStringExtra("title");
        String year = getIntent().getStringExtra("year");
        String location = getIntent().getStringExtra("location");
        String description = getIntent().getStringExtra("description");

        // Set data
        detailImage.setImageResource(imageResId);
        detailTitle.setText(title);
        detailYear.setText("Year: " + year);
        detailLocation.setText("Location: " + location);
        detailDescription.setText(description);
    }
}
