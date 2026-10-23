package com.example.smartpantry;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PantryAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> ingredients;

    public PantryAdapter(
            Activity context,
            ArrayList<String> ingredients) {

        super(context, 0, ingredients);

        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_pantry_ingredient,
                    parent,
                    false
            );
        }

        TextView txtIngredientRow = convertView.findViewById(R.id.txtIngredientRow);

        txtIngredientRow.setText(ingredients.get(position));

        return convertView;
    }
}
