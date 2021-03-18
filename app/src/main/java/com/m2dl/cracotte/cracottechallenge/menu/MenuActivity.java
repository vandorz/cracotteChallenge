package com.m2dl.cracotte.cracottechallenge.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.m2dl.cracotte.cracottechallenge.R;

public class MenuActivity extends AppCompatActivity {
    private Button playButton;
    private Button leaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initComponents();
    }

    private void initComponents() {
        initPlayButton();
        initLeaveButton();
    }

    private void initPlayButton() {
        playButton = findViewById(R.id.menu_button_lancerJeu);
    }

    private void initLeaveButton() {
        leaveButton = findViewById(R.id.menu_button_quitterJeu);
        leaveButton.setOnClickListener(v -> finish());
    }
}