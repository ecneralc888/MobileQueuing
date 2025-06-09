package com.example.mobilequeuing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar); // matches registrar.xml

        // 1) “Get Ticket Number” button
        Button getTicketButton = findViewById(R.id.btn_get_ticket_registrar);
        getTicketButton.setOnClickListener(v ->
                Toast.makeText(RegistrarActivity.this,
                        "Ticket generated! Please wait for your turn.",
                        Toast.LENGTH_SHORT).show()
        );

        // 2) Bottom nav “Home”
        ImageButton homeButton = findViewById(R.id.homeButton_registrar);
        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(RegistrarActivity.this, HomepageActivity.class));
            finish();
        });

        // 3) Bottom nav “List”
        ImageButton queueListButton = findViewById(R.id.nav_list);
        queueListButton.setOnClickListener(v -> {
            startActivity(new Intent(RegistrarActivity.this, QueueActivity.class));
        });
    }
}
