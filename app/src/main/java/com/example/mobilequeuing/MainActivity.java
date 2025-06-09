package com.example.mobilequeuing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginButton;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });

        forgotPassword.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Redirecting to recovery...", Toast.LENGTH_SHORT).show()
        );
    }

    private void loginUser(String email, String password) {
        String url = "http://192.168.1.15/Queing_Enrollment_vanila/php/login.php"; // Replace with your backend URL

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("username", email);  // Assuming your backend uses 'username' field
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Failed to prepare login data", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    boolean success = response.optBoolean("success", false);
                    String message = response.optString("message", "No message from server");
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                    if (success) {
                        // Optionally, get student info from response
                        // JSONObject student = response.optJSONObject("student");
                        // Save user session here if needed

                        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Network error: " + error.getMessage(), Toast.LENGTH_LONG).show()
        );

        Volley.newRequestQueue(MainActivity.this).add(request);
    }
}
