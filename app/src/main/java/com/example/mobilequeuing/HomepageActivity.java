package com.example.mobilequeuing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomepageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        // (Ensure you have res/layout/homepage.xml with the IDs used below)

        // ─── 1) Department “cards” (from homepage.xml) ───
        LinearLayout accountingCard = findViewById(R.id.accountingCard);
        LinearLayout registrarCard  = findViewById(R.id.registrarCard);
        LinearLayout cashierCard    = findViewById(R.id.cashierCard);
        LinearLayout dsaCard        = findViewById(R.id.dsaCard);

        // ─── 2) “Select Service” (Enrollment) button ───
        // (In your homepage.xml, you must have a Button with android:id="@+id/selectServiceButton")
        Button selectServiceButton = findViewById(R.id.selectServiceButton);

        // ─── 3) Bottom‐nav “My Queue” icon (opens QueueActivity) ───
        LinearLayout navMyQueue = findViewById(R.id.navMyQueue);

        // ─── 4) Bottom‐nav “Home” icon ───
        LinearLayout navHome = findViewById(R.id.navHome);

        // ─── 5) Bottom‐nav “Notifications” icon (optional) ───
        LinearLayout navNotification = findViewById(R.id.navNotification);

        // ─── 6) Bottom‐nav “Profile” icon (optional) ───
        LinearLayout navProfile = findViewById(R.id.navProfile);


        // ─── Department click listeners ───
        accountingCard.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, AccountingActivity.class))
        );

        registrarCard.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, RegistrarActivity.class))
        );

        cashierCard.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, CashierActivity.class))
        );

        dsaCard.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, DsaActivity.class))
        );

        // ─── “Select Service” (Enrollment) click listener ───
        selectServiceButton.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, EnrollmentActivity.class))
        );

        // ─── Bottom‐nav “Home” click ───
        navHome.setOnClickListener(v ->
                Toast.makeText(HomepageActivity.this,
                        "You are already on Home",
                        Toast.LENGTH_SHORT).show()
        );

        // ─── Bottom‐nav “My Queue” click ───
        navMyQueue.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, QueueActivity.class))
        );

        // ─── Bottom‐nav “Notifications” click (if you have NotificationActivity) ───
        navNotification.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, NotificationActivity.class))
        );

        // ─── Bottom‐nav “Profile” click (if you have ProfileActivity) ───
        navProfile.setOnClickListener(v ->
                startActivity(new Intent(HomepageActivity.this, ProfileActivity.class))
        );
    }
}
