package com.example.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;

public class EditIngredientActivity extends Activity {

    private EditText editName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiry;

    private DatabaseHelper database;
    private int ingredientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        database = new DatabaseHelper(this);

        ingredientId = getIntent().getIntExtra("ingredient_id", -1);

        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);

        Button btnSave = findViewById(R.id.btnSaveIngredient);
        btnSave.setText("Update Ingredient");
        btnSave.setOnClickListener(v -> updateIngredient());

        Button btnDelete = findViewById(R.id.btnDeleteIngredient);
        btnDelete.setOnClickListener(v -> confirmDelete());

        loadIngredient();



    }

    private void loadIngredient() {

        Cursor cursor = database.getAllIngredients();

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

                if (id == ingredientId) {
                    editName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")
                    ));
                    editQuantity.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"))
                    ));
                    editUnit.setText(cursor.getString(cursor.getColumnIndexOrThrow("unit")
                    ));
                    editExpiry.setText(cursor.getString(cursor.getColumnIndexOrThrow("expiry_date")
                    ));

                    return;
                }
            }
        } finally {
            cursor.close();
        }

        Toast.makeText(this, "Ingredient not found", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void updateIngredient() {

        String name = editName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Enter the name, quantity and unit", Toast.LENGTH_SHORT).show();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);

            if (quantity <= 0) {
                Toast.makeText(this, "Quantity must be greater than zero", Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean updated = database.updateIngredient(ingredientId, name, quantity, unit, expiry);

        if (updated) {
            Toast.makeText(this, "Ingredient updated!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Could not update ingredient", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDelete() {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete this ingredient?")
                .setPositiveButton("Delete", (dialog, which) -> {

                    boolean deleted = database.deleteIngredient(ingredientId);

                    if (deleted) {
                        Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Could not delete ingredient", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}
