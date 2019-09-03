package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_NAME = "name";
    private static final String KEY_NAME_MAIN = "mainName";
    private static final String KEY_NAME_AKA = "alsoKnownAs";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich s = new Sandwich();
        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject nameObject = sandwichJSON.getJSONObject(KEY_NAME);
            s.setMainName(getOptString(nameObject, KEY_NAME_MAIN));

            s.setImage(getOptString(sandwichJSON, KEY_IMAGE));
            s.setAlsoKnownAs(jsonArrayToLst(getOptJSONArray(nameObject, KEY_NAME_AKA)));
            s.setPlaceOfOrigin(getOptString(sandwichJSON, KEY_PLACE_OF_ORIGIN));
            s.setIngredients(jsonArrayToLst(getOptJSONArray(sandwichJSON, KEY_INGREDIENTS)));
            s.setDescription(getOptString(sandwichJSON, KEY_DESCRIPTION));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    private static String getOptString(JSONObject json, String key) {
        try {
            String value = json.getString(key);
            if (!value.isEmpty())
                return value;
        } catch (JSONException ignored) { }
        return null;
    }

    private static JSONArray getOptJSONArray(JSONObject json, String key) {
        try {
            JSONArray array = json.getJSONArray(key);
            if (array.length() > 0)
                return array;
        } catch (JSONException ignored) { }
        return null;
    }
    
    private static List<String> jsonArrayToLst(JSONArray array) {
        if (array == null)
            return null;
        ArrayList<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.get(i).toString());
            }
        } catch (JSONException ignored) { }
        return list;
    }
}
