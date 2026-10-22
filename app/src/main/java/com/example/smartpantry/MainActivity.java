
package com.example.smartpantry;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnMyPantry;
    Button btnAddIngredient;
    Button btnSuggestedRecipes;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the home screen
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        // Connect Java to the XML buttons
        btnMyPantry = findViewById(R.id.btnMyPantry);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        // Temporary actions until we build the other screens
        btnMyPantry.setOnClickListener(v ->
                showMessage("My Pantry"));

        btnAddIngredient.setOnClickListener(v ->
                showMessage("Add Ingredient"));

        btnSuggestedRecipes.setOnClickListener(v ->
                showMessage("Suggested Recipes"));

        btnSettings.setOnClickListener(v ->
                showMessage("Settings"));
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
