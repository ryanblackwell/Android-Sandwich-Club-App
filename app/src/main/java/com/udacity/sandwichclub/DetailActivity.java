package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            findViewById(R.id.also_known_label_tv).setVisibility(View.GONE);
            findViewById(R.id.also_known_tv).setVisibility(View.GONE);
        }
        else {
            ((TextView)findViewById(R.id.also_known_tv))
                    .setText(sandwich.getAlsoKnownAs().toString()
                            .replace("[", "").replace("]", ""));
        }
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            findViewById(R.id.origin_label_tv).setVisibility(View.GONE);
            findViewById(R.id.origin_tv).setVisibility(View.GONE);
        }
        else {
            ((TextView)findViewById(R.id.origin_tv)).setText(sandwich.getPlaceOfOrigin());
        }
        ((TextView)findViewById(R.id.ingredients_tv))
                .setText(sandwich.getIngredients().toString()
                        .replace("[", "").replace("]", ""));
        ((TextView)findViewById(R.id.description_tv)).setText(sandwich.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
