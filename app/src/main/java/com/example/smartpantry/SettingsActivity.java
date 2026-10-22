package com.example.smartpantry;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class SettingsActivity extends Activity {

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        database = new DatabaseHelper(this);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnClearPantry = findViewById(R.id.btnClearPantry);
        TextView txtAppVersion = findViewById(R.id.txtAppVersion);

        txtAppVersion.setText("Smart Pantry Manager\nVersion 1.0");

        btnBack.setOnClickListener(v -> finish());
        btnClearPantry.setOnClickListener(v -> confirmClearPantry());

    }

    private void confirmClearPantry() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Pantry")
                .setMessage("Are you sure you want to remove all ingredients " + "from your pantry? This cannot be undone.")
                .setPositiveButton("Clear", (dialog, which) -> {
                    SQLiteDatabase db = database.getWritableDatabase();

                    db.delete("ingredients", null, null);

                    Toast.makeText(this, "Pantry cleared successfully", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
