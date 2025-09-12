package com.example.historyandculture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LinearLayout nearBySites, virtualTours, historyAndStories, editARArtifacts;
    private FloatingActionButton metaAIButton;
    private FrameLayout scanButton;

    // Bottom navigation Frames
    private FrameLayout exploreFrame, homeFrame, communityFrame;

    // Scanner launcher
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    Toast.makeText(MainActivity.this,
                            "Scanned: " + result.getContents(),
                            Toast.LENGTH_LONG).show();

//                  Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//                  intent.putExtra("scan_result", result.getContents());
//                  startActivity(intent);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        nearBySites = findViewById(R.id.nearbysites);
        virtualTours = findViewById(R.id.virtualtours);
        historyAndStories = findViewById(R.id.historyandstories);
        editARArtifacts = findViewById(R.id.editARartifacts);
        scanButton = findViewById(R.id.scanButton);
        metaAIButton = findViewById(R.id.btnMetaAI);
        exploreFrame = findViewById(R.id.exploreFrame);
        homeFrame = findViewById(R.id.homeFrame);
        communityFrame = findViewById(R.id.communityFrame);

        setActiveTab(homeFrame);
        
        editARArtifacts.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, com.Unity3d.player.UnityPlayerActivity.class)));

        metaAIButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ChatbotActivity.class)));

        historyAndStories.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoryStoriesActivity.class)));

        virtualTours.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, VirtualToursActivity.class)));

        nearBySites.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NearBySitesActivity.class)));

        scanButton.setOnClickListener(v -> openScanner());

        exploreFrame.setOnClickListener(v -> setActiveTab(exploreFrame));
        homeFrame.setOnClickListener(v -> setActiveTab(homeFrame));
        communityFrame.setOnClickListener(v -> setActiveTab(communityFrame));
    }

    private void openScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan a QR or Barcode");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            barcodeLauncher.launch(options);
        } else {
            Toast.makeText(this, "Camera permission is required!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lang_en) {
            setLocale("en");
            return true;
        } else if (id == R.id.lang_hi) {
            setLocale("hi");
            return true;
        } else if (id == R.id.lang_or) {
            setLocale("or");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        recreate();
    }

    private void setActiveTab(FrameLayout selectedFrame) {
        exploreFrame.setSelected(false);
        homeFrame.setSelected(false);
        communityFrame.setSelected(false);

        selectedFrame.setSelected(true);
    }
}