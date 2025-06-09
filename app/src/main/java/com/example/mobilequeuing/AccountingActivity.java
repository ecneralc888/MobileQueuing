package com.example.mobilequeuing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AccountingActivity extends AppCompatActivity {

    private TextView tvNowQueueing;
    private Button btnGetTicket;
    private TextView tvTicketLabel;
    private TextView tvTicketNumber;
    private ImageButton homeButton;
    private ImageButton queueListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) Make sure this matches your XML filename: accounting.xml
        setContentView(R.layout.accounting);

        // 2) Bind the TextViews & Button
        tvNowQueueing    = findViewById(R.id.tv_now_queueing);
        btnGetTicket     = findViewById(R.id.btn_get_ticket);
        tvTicketLabel    = findViewById(R.id.tv_ticket_label);
        tvTicketNumber   = findViewById(R.id.tv_ticket_number);

        // 3) Bottom nav “Home” (ID in accounting.xml was: @+id/nav_home)
        homeButton = findViewById(R.id.nav_home);
        homeButton.setOnClickListener(v -> {
            startActivity(new Intent(AccountingActivity.this, HomepageActivity.class));
            finish();
        });

        // 4) Bottom nav “List” (accounting.xml uses ID=nav_list for the queue icon)
        queueListButton = findViewById(R.id.nav_list);
        queueListButton.setOnClickListener(v -> {
            startActivity(new Intent(AccountingActivity.this, QueueActivity.class));
        });

        // 5) “Get Ticket Number” click listener:
        btnGetTicket.setOnClickListener(v -> {
            // Example hard‐coded next ticket
            String nextTicket = "A037";
            tvTicketNumber.setText(nextTicket);
            tvTicketLabel.setVisibility(View.VISIBLE);
            tvTicketNumber.setVisibility(View.VISIBLE);

            Toast.makeText(AccountingActivity.this,
                    "Ticket generated! Your number is " + nextTicket,
                    Toast.LENGTH_SHORT).show();
        });
    }
}
