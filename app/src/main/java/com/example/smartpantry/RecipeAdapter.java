package com.example.smartpantry;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> recipes;

    public RecipeAdapter(Activity context, ArrayList<String> recipes) {
        super(context, 0, recipes);
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_suggested_recipe, parent, false);
        }

        TextView txtRecipeRowName = convertView.findViewById(R.id.txtRecipeRowName);
        txtRecipeRowName.setText(recipes.get(position));

        return convertView;
    }
}
