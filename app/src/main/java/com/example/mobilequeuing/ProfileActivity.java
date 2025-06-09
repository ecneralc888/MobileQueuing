package com.example.mobilequeuing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1001;

    private ImageView ivProfilePicture;
    private ImageView ivEditPicture; // the little pencil on top of the avatar
    private TextView tvPhone, tvEditPhone;
    private TextView tvAddress, tvEditAddress;
    private MaterialButton btnUpdateProfile, btnSignOut;
    private ImageButton backButton;

    // SharedPreferences file name
    private static final String PREFS_NAME = "UserProfilePrefs";

    // Keys for storing phone, address, and photo URI
    private static final String KEY_PHONE = "pref_phone";
    private static final String KEY_ADDRESS = "pref_address";
    private static final String KEY_PHOTO_URI = "pref_photo_uri";

    // We will hold onto the selected image URI here
    private Uri chosenImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile); // The XML shown above

        // 1) Find all views by ID:
        ivProfilePicture = findViewById(R.id.iv_profile_picture);
        ivEditPicture = findViewById(R.id.iv_edit_profile_picture);
        tvPhone = findViewById(R.id.tv_phone);
        tvEditPhone = findViewById(R.id.tv_edit_phone);
        tvAddress = findViewById(R.id.tv_address);
        tvEditAddress = findViewById(R.id.tv_edit_address);
        btnUpdateProfile = findViewById(R.id.btn_update_profile);
        btnSignOut = findViewById(R.id.btn_sign_out);
        backButton = findViewById(R.id.backButton);

        // 2) Load any previously saved data from SharedPreferences:
        loadSavedProfile();


        // inside onCreate(...) of your Activity:
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // create an Intent to Home
            Intent intent = new Intent(ProfileActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish(); // finish() removes this screen from the back stack
        });


        // 4) Edit avatar: open image picker
        ivEditPicture.setOnClickListener(v -> openImagePicker());

        // 5) Edit phone: show dialog with EditText
        tvEditPhone.setOnClickListener(v -> {
            showEditDialog(
                    "Edit Phone Number",
                    tvPhone.getText().toString(),
                    newValue -> tvPhone.setText(newValue)
            );
        });

        // 6) Edit address: same pattern
        tvEditAddress.setOnClickListener(v -> {
            showEditDialog(
                    "Edit Address",
                    tvAddress.getText().toString(),
                    newValue -> tvAddress.setText(newValue)
            );
        });

        // 7) Update Profile button → save current phone/address/photoUri into SharedPreferences
        btnUpdateProfile.setOnClickListener(v -> {
            String updatedPhone = tvPhone.getText().toString().trim();
            String updatedAddress = tvAddress.getText().toString().trim();

            // Save to SharedPreferences:
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_PHONE, updatedPhone);
            editor.putString(KEY_ADDRESS, updatedAddress);
            if (chosenImageUri != null) {
                editor.putString(KEY_PHOTO_URI, chosenImageUri.toString());
            }
            editor.apply();

            Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
        });

        // 8) Sign Out button → go to MainActivity (login screen)
        btnSignOut.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            // Clear activity stack so user cannot press Back to return here
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Loads saved phone, address, and photo‐URI (if any) from SharedPreferences,
     * and populates the TextViews / ImageView accordingly.
     */
    private void loadSavedProfile() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedPhone = prefs.getString(KEY_PHONE, null);
        String savedAddress = prefs.getString(KEY_ADDRESS, null);
        String savedPhotoUriString = prefs.getString(KEY_PHOTO_URI, null);

        if (savedPhone != null) {
            tvPhone.setText(savedPhone);
        }
        if (savedAddress != null) {
            tvAddress.setText(savedAddress);
        }
        if (savedPhotoUriString != null) {
            chosenImageUri = Uri.parse(savedPhotoUriString);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenImageUri);
                ivProfilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Opens the gallery so the user can pick an image. The result will come to onActivityResult().
     */
    private void openImagePicker() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                chosenImageUri = uri;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), chosenImageUri);
                    ivProfilePicture.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Shows an AlertDialog with a single-line EditText.
     * When the user taps “Save,” it invokes callback.onSaved(newValue).
     *
     * @param title       dialog title (e.g. “Edit Phone Number”)
     * @param initialText prefill text (current phone or address)
     * @param callback    invoked after “Save” is pressed
     */
    private void showEditDialog(String title, String initialText, OnSaveCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setText(initialText);
        input.setSelection(initialText.length());
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newValue = input.getText().toString().trim();
            if (!newValue.isEmpty()) {
                callback.onSaved(newValue);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    /**
     * Simple callback interface for the “Save” button in showEditDialog.
     */
    private interface OnSaveCallback {
        void onSaved(String newValue);
    }
}
