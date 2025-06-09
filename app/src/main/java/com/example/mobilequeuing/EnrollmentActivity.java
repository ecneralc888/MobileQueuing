package com.example.mobilequeuing;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EnrollmentActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button cancelButton;
    private Button nextButton;
    private android.widget.TextView tvStatusCashier;
    private static final String TAG = "EnrollmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollment);

        backButton = findViewById(R.id.backButton);
        cancelButton = findViewById(R.id.cancel_button);
        nextButton = findViewById(R.id.next_button);
        tvStatusCashier = findViewById(R.id.tv_status_cashier);

        backButton.setOnClickListener(v -> {
            Toast.makeText(EnrollmentActivity.this,
                    "Going back to Home",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EnrollmentActivity.this, HomepageActivity.class));
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            new AlertDialog.Builder(EnrollmentActivity.this)
                    .setTitle("Cancel Enrollment")
                    .setMessage("Are you sure you want to cancel your queue?")
                    .setIcon(R.drawable.warning)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        Toast.makeText(EnrollmentActivity.this,
                                "Your queue has been cancelled.",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EnrollmentActivity.this, HomepageActivity.class));
                        finish();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        nextButton.setOnClickListener(v -> {
            String cashierStatus = tvStatusCashier.getText().toString().trim();

            if (cashierStatus.equalsIgnoreCase("Completed")) {
                JSONObject jsonBody = new JSONObject();
                try {
                    int studentId = 123;     // TODO: replace with actual logged in student ID
                    int departmentId = 1;    // TODO: replace with actual selected department ID

                    JSONArray messagesArray = new JSONArray();

                    JSONObject systemMessage = new JSONObject();
                    systemMessage.put("role", "system");
                    systemMessage.put("content", "You are an enrollment assistant.");
                    messagesArray.put(systemMessage);

                    JSONObject userMessage = new JSONObject();
                    userMessage.put("role", "user");
                    userMessage.put("content", "Enroll student ID " + studentId + " to department ID " + departmentId);
                    messagesArray.put(userMessage);

                    jsonBody.put("messages", messagesArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EnrollmentActivity.this, "Failed to prepare enrollment data.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Use 10.0.2.2 for emulator to access host machine's localhost
                String url = "http://10.0.2.16/Queing_Enrollment_vanila/php/openai_proxy.php";

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        jsonBody,
                        response -> {
                            Log.d(TAG, "Response: " + response.toString());
                            Toast.makeText(EnrollmentActivity.this, "Response: " + response.toString(), Toast.LENGTH_LONG).show();

                            try {
                                JSONArray choices = response.getJSONArray("choices");
                                String reply = choices.getJSONObject(0).getJSONObject("message").getString("content");
                                Toast.makeText(EnrollmentActivity.this, reply, Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            Log.e(TAG, "Network error", error);

                            if (error.networkResponse != null) {
                                int statusCode = error.networkResponse.statusCode;
                                String body = "";
                                try {
                                    body = new String(error.networkResponse.data, "UTF-8");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.e(TAG, "Error Status Code: " + statusCode);
                                Log.e(TAG, "Error Body: " + body);
                            }

                            String message = error.getMessage();
                            if (message == null || message.isEmpty()) {
                                message = "Unknown network error occurred.";
                            }
                            Toast.makeText(EnrollmentActivity.this, "Network error: " + message, Toast.LENGTH_LONG).show();
                        }
                );

                Volley.newRequestQueue(EnrollmentActivity.this).add(request);

            } else {
                Toast.makeText(EnrollmentActivity.this,
                        "Please wait until Cashier is marked “Completed” before proceeding.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
