package com.example.smartpantry;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetailActivity extends Activity {

    private DatabaseHelper database;
    private TextView txtRecipeName;
    private TextView txtRecipeDescription;
    private TextView txtRecipeInstructions;
    private TextView txtRecipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        database = new DatabaseHelper(this);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeDescription = findViewById(R.id.txtRecipeDescription);
        txtRecipeInstructions = findViewById(R.id.txtRecipeInstructions);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId == -1) {
            Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadRecipe(recipeId);
        loadRecipeIngredients(recipeId);
    }

    private void loadRecipe(int recipeId) {
        Cursor cursor = database.getRecipeById(recipeId);

        try {
            if (cursor.moveToFirst()) {
                txtRecipeName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name"))
                );
                txtRecipeDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow("description"))
                );
                txtRecipeInstructions.setText(cursor.getString(cursor.getColumnIndexOrThrow("instructions"))
                );
            } else {
                Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show();
                finish();
            }
        } finally {
            cursor.close();
        }
    }

        private void loadRecipeIngredients ( int recipeId){

            Cursor cursor = database.getReadableDatabase().rawQuery("SELECT ingredient_name, required_quantity, unit " +
                    "FROM recipe_ingredients WHERE recipe_id = ?", new String[]{String.valueOf(recipeId)});

            StringBuilder ingredients = new StringBuilder();

            try {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    double quantity = cursor.getDouble(1);
                    String unit = cursor.getString(2);

                    ingredients.append("• ")
                            .append(quantity)
                            .append(" ")
                            .append(unit)
                            .append(" ")
                            .append(name)
                            .append("\n");
                }
            } finally {
                cursor.close();
            }

            if (ingredients.length() == 0) {
                txtRecipeIngredients.setText(
                        "No ingredients listed for this recipe.");
            } else {
                txtRecipeIngredients.setText(ingredients.toString());
            }
        }
    }

