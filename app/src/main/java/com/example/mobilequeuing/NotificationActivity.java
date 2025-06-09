package com.example.mobilequeuing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        // 1) Find the back-arrow ImageButton
        ImageButton backButton = findViewById(R.id.backButton);

        // 2) Wire it up to navigate back to HomepageActivity
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(NotificationActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
