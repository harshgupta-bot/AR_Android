package com.example.historyandculture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

   TextView generalOption, aboutOption;
    TextView signOutOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        generalOption = findViewById(R.id.general);
        aboutOption = findViewById(R.id.about);
        signOutOption = findViewById(R.id.signOut);

        // General Click
        generalOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "General Settings Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(SettingsActivity.this, GeneralActivity.class));
            }
        });

        aboutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "About Clicked", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
            }
        });

        signOutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Signed Out", Toast.LENGTH_SHORT).show();
                // Yaha logout logic likho
                finish();
            }
        });
    }
}
