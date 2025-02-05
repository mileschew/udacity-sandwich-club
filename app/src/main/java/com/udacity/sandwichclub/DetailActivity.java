package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView akaTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;
    private TextView originTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        akaTv = findViewById(R.id.also_known_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        originTv = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.sandwich_placeholder)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwichData) {
        if (sandwichData.getAlsoKnownAs() != null)
            akaTv.setText(sandwichData.getAlsoKnownAs().toString());
        if (sandwichData.getDescription() != null)
            descriptionTv.setText(sandwichData.getDescription());
        if (sandwichData.getIngredients() != null)
            ingredientsTv.setText(sandwichData.getIngredients().toString());
        if (sandwichData.getPlaceOfOrigin() != null)
            originTv.setText(sandwichData.getPlaceOfOrigin());
    }
}
