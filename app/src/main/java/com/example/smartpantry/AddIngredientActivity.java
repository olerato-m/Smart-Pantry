package com.example.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddIngredientActivity extends Activity {

    private EditText editName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiry;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);
        btnSave = findViewById(R.id.btnSaveIngredient);

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = editName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Please enter the name, quantity and unit", Toast.LENGTH_SHORT).show();
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

        DatabaseHelper db = new DatabaseHelper(this);
        boolean result = db.addIngredient(name, quantity, unit, expiry);

        if (result) {
            Toast.makeText(this, "Ingredient saved!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Could not save ingredient", Toast.LENGTH_SHORT).show();
        }
    }
}
