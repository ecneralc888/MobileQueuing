package com.example.mobilequeuing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CashierActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cashier); // matches cashier.xml

        // 1) “Get Ticket Number” button
        Button getTicketButton = findViewById(R.id.btn_get_ticket_cashier);
        getTicketButton.setOnClickListener(v ->
                Toast.makeText(CashierActivity.this,
                        "Ticket generated! Please wait for your turn.",
                        Toast.LENGTH_SHORT).show()
        );

        // 2) Bottom nav “Home”
        ImageButton homeButton = findViewById(R.id.homeButton_cashier);
        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(CashierActivity.this, HomepageActivity.class));
            finish();
        });

        // 3) Bottom nav “List”
        ImageButton queueListButton = findViewById(R.id.nav_list);
        queueListButton.setOnClickListener(v -> {
            startActivity(new Intent(CashierActivity.this, QueueActivity.class));
        });
    }
}
