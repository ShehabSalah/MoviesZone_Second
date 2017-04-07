package com.platformhouse.movieszone.movieszone.parser;

import com.google.gson.Gson;
import com.platformhouse.movieszone.movieszone.data.movie.MovieColumnHolder;
import com.platformhouse.movieszone.movieszone.data.review.ReviewColumnHolder;
import com.platformhouse.movieszone.movieszone.data.trailer.TrailerColumnHolder;
import com.platformhouse.movieszone.movieszone.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class is responsible on parsing the JSONString based on the type of the coming JSONString
 * Types Of JSONString: JsonString coming from /popular or /top_rated,
 *                      JsonString coming from {movieID}/reviews,
 *                      JsonString coming from {movieID}/videos.
 */
public class Parser {
    public ArrayList<Object> JsonParser (String json, int type) throws JSONException{
        //Convert the JsonString to JsonObject
        JSONObject jsonObject = new JSONObject(json);
        //Convert the JsonObject to JsonArray
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        //Get object of Gson() class
        Gson gson = new Gson();
        //ArrayList of Objects to hold the parsing json
        ArrayList<Object> list = new ArrayList<>();
        //Based on the type of the coming JsonString parse and fill the list
        switch (type){
            case Constants.movieColumn:
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    MovieColumnHolder movieColumnHolder =
                            gson.fromJson(jsonObject1.toString(),MovieColumnHolder.class);
                    list.add(movieColumnHolder);

                }
                return list;
            case Constants.reviewColumn:
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ReviewColumnHolder reviewColumnHolder =
                            gson.fromJson(jsonObject1.toString(),ReviewColumnHolder.class);
                    list.add(reviewColumnHolder);
                }
                return list;
            case Constants.trailerColumn:
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    TrailerColumnHolder trailerColumnHolder =
                            gson.fromJson(jsonObject1.toString(),TrailerColumnHolder.class);
                    list.add(trailerColumnHolder);
                }
                return list;
        }
        return null;
    }
}
