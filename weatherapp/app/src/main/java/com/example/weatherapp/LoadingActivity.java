package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);

    }
}