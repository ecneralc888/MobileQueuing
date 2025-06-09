package com.example.mobilequeuing;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QueueActivity extends AppCompatActivity {

    Spinner departmentSpinner;
    Button notifyButton, leaveQueueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queueing); // Make sure you have res/layout/queue.xml

        // 1) “Back” arrow in the toolbar (ImageButton with id=backButton)
        ImageButton backButton = findViewById(R.id.btn_back_queueing);
        backButton.setOnClickListener(v -> {
            // Return to Home
            startActivity(new Intent(QueueActivity.this, HomepageActivity.class));
            finish();
        });

        // 2) Find Spinner + Buttons
        departmentSpinner   = findViewById(R.id.spinner_dept);
        notifyButton        = findViewById(R.id.btn_notify_me);
        leaveQueueButton    = findViewById(R.id.btn_leave_queue);

        // 3) Populate Spinner with the four departments
        String[] departments = {"Accounting", "Registrar", "Cashier", "Advising"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                departments
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departmentSpinner.setAdapter(adapter);

        // 4) Tap “Notify Me” → just show a toast for now
        notifyButton.setOnClickListener(v ->
                Toast.makeText(QueueActivity.this, "You will be notified soon!", Toast.LENGTH_SHORT).show()
        );

        ImageButton btn_back_queueing = findViewById(R.id.btn_back_queueing);
        backButton.setOnClickListener(v -> {
            // When clicked, return to HomepageActivity
            Intent intent = new Intent(QueueActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish(); // Close NotificationActivity so the user cannot “back” into it
        });

        // 5) Tap “Leave Queue” → confirmation dialog
        leaveQueueButton.setOnClickListener(v -> {
            new AlertDialog.Builder(QueueActivity.this)
                    .setTitle("Leave Queue")
                    .setMessage("Are you sure you want to leave the queue?")
                    .setIcon(R.drawable.warning) // replace with your warning icon if you have one
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Toast.makeText(QueueActivity.this,
                                "You have left the queue.",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
