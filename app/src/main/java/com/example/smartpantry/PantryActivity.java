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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        database = new DatabaseHelper(this);

        listPantry = findViewById(R.id.listPantry);
        txtPantryCount = findViewById(R.id.txtPantryCount);
        Button btnAddPantryItem = findViewById(R.id.btnAddPantryItem);

        ingredientNames = new ArrayList<>();

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                ingredientNames
        );

        listPantry.setAdapter(adapter);

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

        Cursor cursor = database.getAllIngredients();

        try {
            while (cursor.moveToNext()) {
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name"));

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow("quantity"));

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow("unit"));

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
