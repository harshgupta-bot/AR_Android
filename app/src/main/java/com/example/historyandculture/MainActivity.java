package com.example.historyandculture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.historyandculture.adapter.CardAdapter;
import com.example.historyandculture.model.CardModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FrameLayout exploreFrame, homeFrame, communityFrame;
    private ImageView profileImage;
    private FloatingActionButton metaAIButton;
    private FrameLayout scanButton;
    private RecyclerView gridRecycler;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    Toast.makeText(MainActivity.this,
                            "Scanned: " + result.getContents(),
                            Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);

        profileImage = findViewById(R.id.profileImage);
        exploreFrame = findViewById(R.id.exploreFrame);
        homeFrame = findViewById(R.id.homeFrame);
        communityFrame = findViewById(R.id.communityFrame);
        metaAIButton = findViewById(R.id.btnMetaAI);
        scanButton = findViewById(R.id.scanButton);
        gridRecycler = findViewById(R.id.gridRecycler);

        profileImage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        exploreFrame.setOnClickListener(v -> setActiveTab(exploreFrame));
        homeFrame.setOnClickListener(v -> setActiveTab(homeFrame));
        communityFrame.setOnClickListener(v -> setActiveTab(communityFrame));
        // Set default tab
        setActiveTab(homeFrame);


        metaAIButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ChatbotActivity.class)));

        scanButton.setOnClickListener(v -> openScanner());

        setupCards();

        if (savedInstanceState == null) {
            new Handler().postDelayed(() -> {
                recreate();
            }, 200);
        }
    }

    private void setupCards() {
        List<CardModel> cardList = new ArrayList<>();
        cardList.add(new CardModel(R.drawable.ic_nearby1, getString(R.string.nearby_sites), getString(R.string.nearby_sites_desc)));
        cardList.add(new CardModel(R.drawable.ic_tour,  getString(R.string.virtual_tours), getString(R.string.virtual_tours_desc)));
        cardList.add(new CardModel(R.drawable.ic_book, getString(R.string.history_stories), getString(R.string.history_stories_desc)));
        cardList.add(new CardModel(R.drawable.ic_building, getString(R.string.ar_artifacts), getString(R.string.ar_artifacts_desc)));

        CardAdapter adapter = new CardAdapter(this, cardList, position -> {
            switch (position) {
                case 0: startActivity(new Intent(this, MapActivity.class)); break;
                case 1: startActivity(new Intent(this, VirtualToursActivity.class)); break;
                case 2: startActivity(new Intent(this, HistoryStoriesActivity.class)); break;
                case 3: startActivity(new Intent(this, ARActivity.class)); break;
            }
        });

        gridRecycler.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        gridRecycler.setAdapter(adapter);
    }

    private void openScanner() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan a QR or Barcode");
            options.setBeepEnabled(true);
            options.setOrientationLocked(false);
            options.setCaptureActivity(CustomScannerActivity.class);
            barcodeLauncher.launch(options);
        } else {
            Toast.makeText(this, "Camera permission is required!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setActiveTab(FrameLayout selectedFrame) {
        // Reset all tabs
        exploreFrame.setSelected(false);
        homeFrame.setSelected(false);
        communityFrame.setSelected(false);

        // Set selected tab
        selectedFrame.setSelected(true);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lang_en) { setLocale("en"); return true; }
        if (id == R.id.lang_hi) { setLocale("hi"); return true; }
        if (id == R.id.lang_or) { setLocale("or"); return true; }
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
}
