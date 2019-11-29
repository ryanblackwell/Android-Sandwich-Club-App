package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject sandwichObject = new JSONObject(json);
            String mainName = sandwichObject.getJSONObject("name").getString("mainName");
            String placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            String description = sandwichObject.getString("description");
            String image = sandwichObject.getString("image");
            JSONArray alsoKnownAsJArray = sandwichObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJArray.getString(i));
            }
            JSONArray ingredientsJArray = sandwichObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsJArray.length(); i++) {
                ingredients.add(ingredientsJArray.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
