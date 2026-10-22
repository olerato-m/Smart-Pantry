
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        database = new DatabaseHelper(this);

        txtRecipeName = findViewById(R.id.txtRecipeName);
        txtRecipeDescription = findViewById(R.id.txtRecipeDescription);
        txtRecipeInstructions = findViewById(R.id.txtRecipeInstructions);

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        int recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId == -1) {
            Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadRecipe(recipeId);
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
            }
            else{
                Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        finally{
            cursor.close();
        }
    }
}
