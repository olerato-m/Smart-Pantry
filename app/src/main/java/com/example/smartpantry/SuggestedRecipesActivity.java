package com.example.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends Activity {

    private DatabaseHelper database;
    private ListView listRecipes;
    private TextView txtMessage;

    private ArrayList<Integer> recipeIds;
    private ArrayList<String> recipeNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        database = new DatabaseHelper(this);

        listRecipes = findViewById(R.id.listRecipes);
        txtMessage = findViewById(R.id.txtMessage);

        Button btnBack = findViewById(R.id.btnBack);

        recipeIds = new ArrayList<>();
        recipeNames = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                recipeNames
        );

        listRecipes.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        listRecipes.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(
                    SuggestedRecipesActivity.this,
                    RecipeDetailActivity.class
            );

            intent.putExtra("recipe_id", recipeIds.get(position));
            startActivity(intent);
                }
        );

        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        recipeIds.clear();
        recipeNames.clear();

        Cursor cursor = database.getSuggestedRecipes();

        try {
            while (cursor.moveToNext()) {
                int recipeId = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );

                String recipeName = cursor.getString(
                        cursor.getColumnIndexOrThrow("name")
                );

                recipeIds.add(recipeId);
                recipeNames.add(recipeName);
            }
        } finally {
            cursor.close();
        }

        adapter.notifyDataSetChanged();

        if (recipeNames.isEmpty()) {
            txtMessage.setText(
                    "No recipes available yet. Add more ingredients " +
                            "or check their quantities in your pantry."
            );
        } else {
            txtMessage.setText(
                    "Recipes you can make: " + recipeNames.size()
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (database != null && adapter != null) {
            loadSuggestedRecipes();
        }
    }
}
