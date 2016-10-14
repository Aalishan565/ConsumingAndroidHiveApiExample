package com.consumingandroidhiveapiexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by aalishan on 13/10/16.
 */
public class MovieAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<MovieModel> movielist;
    MovieModel m;
    Context context;
    ImageLoader imageLoader = VollySingleton.getInstance().getImageLoader();

    public MovieAdapter(Context ctx, List<MovieModel> list) {
        inflater = LayoutInflater.from(ctx);
        context = ctx;
        movielist = list;


    }

    @Override
    public int getCount() {
        return movielist.size();
    }

    @Override
    public Object getItem(int position) {
        return movielist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_item, null);
            viewHolder.iv_image = (NetworkImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.tvMovieName = (TextView) convertView.findViewById(R.id.tv_movie_name);
            viewHolder.tvRating = (TextView) convertView.findViewById(R.id.tv_rating);
            viewHolder.tvCarg = (TextView) convertView.findViewById(R.id.tv_category);
            viewHolder.tvYear = (TextView) convertView.findViewById(R.id.tv_year);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.iv_image.setImageUrl(movielist.get(position).getImage(), imageLoader);
        viewHolder.tvMovieName.setText(movielist.get(position).getTitle());
        viewHolder.tvRating.setText("Rating : " + movielist.get(position).getRating());
        viewHolder.tvYear.setText("Year : " + movielist.get(position).getReleaseYear());

        // genre
        m = movielist.get(position);
        String genreStr = "";
        for (String str : m.getGenre()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        viewHolder.tvCarg.setText("Carg : " + genreStr);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("movieName",movielist.get(position).getTitle());
                i.putExtra("Rating",movielist.get(position).getRating());
                i.putExtra("Year",movielist.get(position).getReleaseYear());
                i.putExtra("imageUrl",movielist.get(position).getImage());
                context.startActivity(i);
                //Toast.makeText(context, "" + movielist.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    class ViewHolder {
        private NetworkImageView iv_image;
        private TextView tvMovieName;
        private TextView tvRating;
        private TextView tvCarg;
        private TextView tvYear;


    }
}
