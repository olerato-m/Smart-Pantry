package com.example.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.util.ArrayList;

public class PantryActivity extends Activity {

    private DatabaseHelper database;
    private ListView listPantry;
    private TextView txtPantryCount;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> ingredientNames;
    private ArrayList<Integer> ingredientIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        database = new DatabaseHelper(this);

        listPantry = findViewById(R.id.listPantry);
        txtPantryCount = findViewById(R.id.txtPantryCount);
        Button btnAddPantryItem = findViewById(R.id.btnAddPantryItem);

        ingredientNames = new ArrayList<>();
        ingredientIds = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                ingredientNames
        );

        listPantry.setAdapter(adapter);

        listPantry.setOnItemClickListener((parent, view, position, id) -> {

            if (position < 0 || position >= ingredientIds.size()) {
                return;
            }

            int ingredientId = ingredientIds.get(position);

            Intent intent = new Intent(PantryActivity.this, EditIngredientActivity.class);

            intent.putExtra("ingredient_id", ingredientId);

            startActivity(intent);
        });

        btnAddPantryItem.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );

            startActivity(intent);
        });

        loadIngredients();
    }

    private void loadIngredients() {
        ingredientNames.clear();
        ingredientIds.clear();

        Cursor cursor = database.getAllIngredients();

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow("id")
                );
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name"));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity"));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit"));

                ingredientIds.add(id);

                ingredientNames.add(
                        name + " — " + quantity + " " + unit);
            }
        } finally {
            cursor.close();
        }

        adapter.notifyDataSetChanged();

        txtPantryCount.setText(
                "Ingredients in your pantry: " + ingredientNames.size());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (database != null && adapter != null) {
            loadIngredients();
        }
    }
}
