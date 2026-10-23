
package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    Button btnMyPantry;
    Button btnAddIngredient;
    Button btnSuggestedRecipes;
    Button btnSettings;

    private DatabaseHelper dbHelper;
    private TextView txtIngredientCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DatabaseHelper(this);
        txtIngredientCount = findViewById(R.id.txtIngredientCount);

        // Display the home screen
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        // Connect Java to the XML buttons
        txtIngredientCount = findViewById(R.id.txtIngredientCount);
        btnMyPantry = findViewById(R.id.btnMyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        // Temporary actions until we build the other screens
        btnMyPantry.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,
                    PantryActivity.class));
        });

        btnAddIngredient.setOnClickListener(v -> {
            startActivity(new Intent(
                    MainActivity.this,
                    AddIngredientActivity.class
            ));
        });

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,
                    SuggestedRecipesActivity.class
        );

        startActivity(intent);
    });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Refresh the count whenever Home becomes visible
        updateIngredientCount();
    }

    private void updateIngredientCount() {

        Cursor cursor = dbHelper.getAllIngredients();

        int count = 0;

        try {
            while (cursor.moveToNext()) {
                count++;
            }
        } finally {
            cursor.close();
        }

        txtIngredientCount.setText(
                count + (count == 1 ? " ingredient" : " ingredients")
        );
    }
}



