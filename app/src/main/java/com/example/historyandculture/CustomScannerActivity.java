package com.example.historyandculture;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.journeyapps.barcodescanner.CaptureActivity;

    public class CustomScannerActivity extends CaptureActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

// ye mera optionally code hai yadi mujhe sanner pr border line dikhana ho to eska use kar sakte hai warna nhi bhi
            View overlay = LayoutInflater.from(this)
                    .inflate(R.layout.custom_scanner_overlay, null);
            addContentView(overlay,
                    new android.widget.FrameLayout.LayoutParams(
                            android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                            android.widget.FrameLayout.LayoutParams.MATCH_PARENT));
        }
    }
