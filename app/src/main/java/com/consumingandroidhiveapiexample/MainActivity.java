package com.consumingandroidhiveapiexample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mLvMovies;
    private String TAG = "Main Activity";
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private List<MovieModel> mListMovieModel;
    private MovieAdapter mMovieAdapter;
    private static final String url = "http://api.androidhive.info/json/movies.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(MainActivity.this);
        mListMovieModel = new ArrayList<>();
        requestQueue = VollySingleton.getInstance().getRequestQueue();
        mLvMovies = (ListView) findViewById(R.id.lv_movies);
        loadData();
    }

    private void loadData() {
        progressDialog.setMessage("loading");
        progressDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("data", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    MovieModel movieModel = new MovieModel();
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        movieModel.setTitle(obj.getString("title"));
                        movieModel.setImage(obj.getString("image"));

                        movieModel.setRating(((Number) obj.get("rating"))
                                .doubleValue());
                        movieModel.setReleaseYear(obj.getInt("releaseYear"));
                        // Genre is json array
                        JSONArray genreArry = obj.getJSONArray("genre");
                        ArrayList<String> genre = new ArrayList<>();
                        for (int j = 0; j < genreArry.length(); j++) {
                            genre.add((String) genreArry.get(j));
                        }
                        movieModel.setGenre(genre);
                        mListMovieModel.add(movieModel);
                        mMovieAdapter = new MovieAdapter(MainActivity.this, mListMovieModel);
                        mLvMovies.setAdapter(mMovieAdapter);
                        progressDialog.hide();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();

            }
        });
        requestQueue.add(jsonArrayRequest);

    }

}
