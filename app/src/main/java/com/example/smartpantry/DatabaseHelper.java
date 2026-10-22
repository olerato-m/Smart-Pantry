
package com.example.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        seedRecipesIfNeeded();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "expiry_date TEXT)");

        db.execSQL("CREATE TABLE recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "description TEXT, " +
                "instructions TEXT)");

        db.execSQL("CREATE TABLE recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, " +
                "ingredient_name TEXT NOT NULL, " +
                "required_quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "FOREIGN KEY(recipe_id) REFERENCES recipes(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Keep existing data. Add future database migrations here.
    }

    // Add an ingredient to the pantry.
    public boolean addIngredient(String name, double quantity, String unit, String expiryDate) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name.trim());
        values.put("quantity", quantity);
        values.put("unit", unit.trim());
        values.put("expiry_date", expiryDate);

        return db.insert("ingredients", null, values) != -1;
    }

    // Read all pantry ingredients.
    public Cursor getAllIngredients() {
        return getReadableDatabase().rawQuery("SELECT * FROM ingredients ORDER BY name ASC", null);
    }

    // Delete an ingredient.
    public boolean deleteIngredient(int id){
        return getWritableDatabase().delete("ingredients", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    // Add the sample recipes only when the recipe table is empty.
    private void seedRecipesIfNeeded(){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM recipes", null);

        int count = 0;
        if (cursor.moveToFirst()){
            count = cursor.getInt(0);
        }
        cursor.close();

        if (count > 0){
            return;
        }

        db.beginTransaction();
        try {
            seedRecipes(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // Each ingredient is: name, quantity, unit.
    private void seedRecipes(SQLiteDatabase db) {

        addRecipe(db, "Cheese Omelette", "A quick egg and cheese breakfast.", "Beat the eggs, cook in a pan and add cheese.",
                new String[][]{
                        {"egg", "2", "piece"},
                        {"cheese", "30", "g"}
                });

        addRecipe(db, "Tomato Pasta", "Simple pasta with a tomato sauce.", "Boil pasta. Cook tomato with onion and mix together.",
                new String[][]{
                        {"pasta", "100", "g"},
                        {"tomato", "2", "piece"},
                        {"onion", "1", "piece"}
                });

        addRecipe(db, "Banana Pancakes", "Easy banana pancakes.", "Mash banana, mix with egg and flour, then fry.",
                new String[][]{
                        {"banana", "1", "piece"},
                        {"egg", "1", "piece"},
                        {"flour", "100", "g"}
                });

        addRecipe(db, "Potato Mash", "Creamy mashed potatoes.", "Boil potatoes until soft, then mash with butter and milk.",
                new String[][]{
                        {"potato", "2", "piece"},
                        {"butter", "20", "g"},
                        {"milk", "50", "ml"}
                });

        addRecipe(db, "Peanut Butter Toast", "A simple toasted snack.", "Toast bread and spread peanut butter.",
                new String[][]{
                        {"bread", "2", "slice"},
                        {"peanut butter", "2", "tbsp"}
                });

        addRecipe(db, "Cheese Sandwich", "A classic cheese sandwich.", "Place cheese between bread slices.",
                new String[][]{
                        {"bread", "2", "slice"},
                        {"cheese", "40", "g"}
                });

        addRecipe(db, "Fried Rice", "Rice stir-fried with vegetables and egg.", "Fry carrot and onion, add cooked rice and egg.",
                new String[][]{
                        {"rice", "150", "g"},
                        {"carrot", "1", "piece"},
                        {"onion", "1", "piece"},
                        {"egg", "1", "piece"}
                });

        addRecipe(db, "Carrot Soup", "A warm carrot soup.", "Boil carrots and onion, blend, then add milk.",
                new String[][]{
                        {"carrot", "3", "piece"},
                        {"onion", "1", "piece"},
                        {"milk", "100", "ml"}
                });

        addRecipe(db, "Apple Oatmeal", "Warm oats topped with apple.", "Cook oats with milk and serve with chopped apple.",
                new String[][]{
                        {"oats", "50", "g"},
                        {"milk", "200", "ml"},
                        {"apple", "1", "piece"}
                });

        addRecipe(db, "Garlic Bread", "Crispy garlic bread.", "Mix garlic into butter, spread on bread and toast.",
                new String[][]{
                        {"bread", "2", "slice"},
                        {"garlic", "1", "clove"},
                        {"butter", "20", "g"}
                });

        addRecipe(db, "Tomato Egg Scramble", "Scrambled eggs with tomato.", "Cook tomato, add beaten eggs and stir until cooked.",
                new String[][]{
                        {"egg", "2", "piece"},
                        {"tomato", "1", "piece"}
                });

        addRecipe(db, "Bean Salad", "A simple bean and onion salad.", "Mix cooked beans with chopped onion and tomato.",
                new String[][]{
                        {"beans", "150", "g"},
                        {"onion", "1", "piece"},
                        {"tomato", "1", "piece"}
                });

        addRecipe(db, "Chicken Rice", "A basic chicken and rice meal.", "Cook chicken thoroughly and serve with cooked rice.",
                new String[][]{
                        {"chicken", "150", "g"},
                        {"rice", "150", "g"}
                });

        addRecipe(db, "Tuna Sandwich", "A quick tuna sandwich.", "Mix tuna with mayonnaise and place on bread.",
                new String[][]{
                        {"tuna", "100", "g"},
                        {"mayonnaise", "1", "tbsp"},
                        {"bread", "2", "slice"}
                });

        addRecipe(db, "Spinach Omelette", "Eggs cooked with spinach.", "Cook spinach, add beaten eggs and cook until set.",
                new String[][]{
                        {"egg", "2", "piece"},
                        {"spinach", "50", "g"}
                });

        addRecipe(db, "Yoghurt Fruit Bowl", "Fruit served with yoghurt.", "Chop the fruit and serve with yoghurt.",
                new String[][]{
                        {"banana", "1", "piece"},
                        {"apple", "1", "piece"},
                        {"yoghurt", "100", "g"}
                });

        addRecipe(db, "Corn Fritters", "Pan-fried sweetcorn fritters.", "Mix corn with egg and flour, then fry spoonfuls.",
                new String[][]{
                        {"corn", "100", "g"},
                        {"egg", "1", "piece"},
                        {"flour", "50", "g"}
                });

        addRecipe(db, "Creamy Mushroom Pasta", "Pasta in a creamy mushroom sauce.", "Cook pasta. Fry mushrooms, add cream and combine.",
                new String[][]{
                        {"pasta", "100", "g"},
                        {"mushroom", "100", "g"},
                        {"cream", "50", "ml"}
                });

        addRecipe(db, "Avocado Toast", "Mashed avocado on toast.", "Mash avocado and spread over toasted bread.",
                new String[][]{
                        {"avocado", "1", "piece"},
                        {"bread", "2", "slice"}
                });

        addRecipe(db, "Tomato Cheese Toast", "Toasted bread with tomato and cheese.", "Add sliced tomato and cheese to bread and toast.",
                new String[][]{
                        {"bread", "2", "slice"},
                        {"tomato", "1", "piece"},
                        {"cheese", "30", "g"}
                });
    }

    private void addRecipe(SQLiteDatabase db, String name, String description, String instructions, String[][] ingredients) {

        ContentValues recipe = new ContentValues();
        recipe.put("name", name);
        recipe.put("description", description);
        recipe.put("instructions", instructions);

        long recipeId = db.insert("recipes", null, recipe);

        if (recipeId == -1) {
            return;
        }

        for (String[] item : ingredients) {
            ContentValues ingredient = new ContentValues();
            ingredient.put("recipe_id", recipeId);
            ingredient.put("ingredient_name", item[0]);
            ingredient.put("required_quantity",
                    Double.parseDouble(item[1]));
            ingredient.put("unit", item[2]);

            db.insert("recipe_ingredients", null, ingredient);
        }
    }

    // Return only recipes for which every ingredient is available.
    public Cursor getSuggestedRecipes() {

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {
                "id", "name", "description", "instructions"
        };

        MatrixCursor suggestions = new MatrixCursor(columns);

        Cursor recipes = db.rawQuery("SELECT id, name, description, instructions FROM recipes", null);

        try {
            while (recipes.moveToNext()) {
                int recipeId = recipes.getInt(0);

                if (canMakeRecipe(db, recipeId)) {
                    suggestions.addRow(new Object[]{
                            recipes.getInt(0),
                            recipes.getString(1),
                            recipes.getString(2),
                            recipes.getString(3)
                    });
                }
            }
        } finally {
            recipes.close();
        }

        return suggestions;
    }

    private boolean canMakeRecipe(SQLiteDatabase db, int recipeId) {

        Cursor required = db.rawQuery("SELECT ingredient_name, required_quantity, unit " + "FROM recipe_ingredients WHERE recipe_id = ?", new String[]{String.valueOf(recipeId)});

        try {
            if (!required.moveToFirst()) {
                return false;
            }

            do {
                String requiredName = normalizeName(required.getString(0));
                double requiredQuantity = required.getDouble(1);
                String requiredUnit = normalizeUnit(required.getString(2));

                double availableQuantity = 0;

                Cursor pantry = db.rawQuery("SELECT name, quantity, unit FROM ingredients", null);

                try {
                    while (pantry.moveToNext()) {
                        String pantryName = normalizeName(pantry.getString(0));
                        String pantryUnit = normalizeUnit(pantry.getString(2));

                        if (requiredName.equals(pantryName)
                                && requiredUnit.equals(pantryUnit)) {
                            availableQuantity += pantry.getDouble(1);
                        }
                    }
                } finally {
                    pantry.close();
                }

                // Missing ingredient or insufficient quantity:
                // reject the entire recipe.
                if (availableQuantity < requiredQuantity) {
                    return false;
                }

            } while (required.moveToNext());

            return true;

        } finally {
            required.close();
        }
    }

    // Treat common singular and plural ingredient names as equivalent.
    private String normalizeName(String name) {
        String value = name.trim().toLowerCase(Locale.ROOT);

        if (value.endsWith("ies") && value.length() > 3) {
            return value.substring(0, value.length() - 3) + "y";
        }

        if (value.endsWith("oes") && value.length() > 3) {
            return value.substring(0, value.length() - 2);
        }

        if (value.endsWith("s") && !value.endsWith("ss")
                && value.length() > 1) {
            return value.substring(0, value.length() - 1);
        }

        return value;
    }

    // Standardise common unit spellings.
    private String normalizeUnit(String unit) {
        String value = unit.trim().toLowerCase(Locale.ROOT);

        switch (value) {
            case "pieces":
            case "piece":
            case "pcs":
                return "piece";

            case "slices":
            case "slice":
                return "slice";

            case "grams":
            case "gram":
            case "g":
                return "g";

            case "millilitres":
            case "milliliters":
            case "millilitre":
            case "milliliter":
            case "ml":
                return "ml";

            case "cups":
            case "cup":
                return "cup";

            case "tablespoons":
            case "tablespoon":
            case "tbsp":
                return "tbsp";

            case "teaspoons":
            case "teaspoon":
            case "tsp":
                return "tsp";

            case "cloves":
            case "clove":
                return "clove";

            default:
                return value;
        }
    }
}
