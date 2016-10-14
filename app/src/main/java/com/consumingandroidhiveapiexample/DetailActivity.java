package com.consumingandroidhiveapiexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class DetailActivity extends AppCompatActivity {
    private NetworkImageView imageView;
    private ImageLoader imageLoader = VollySingleton.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView= (NetworkImageView) findViewById(R.id.iv_image);
        String movieName = getIntent().getExtras().getString("movieName");
        double Rating = getIntent().getExtras().getDouble("Rating");
        int Year = getIntent().getExtras().getInt("Year");
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        imageView.setImageUrl(imageUrl, imageLoader);


        Toast.makeText(DetailActivity.this, "" + movieName + Rating + Year, Toast.LENGTH_LONG).show();
    }
}
