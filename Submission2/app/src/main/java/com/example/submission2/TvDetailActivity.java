package com.example.submission2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Objects;

public class TvDetailActivity extends AppCompatActivity {
    public static final String TAG_DETAIL_TV = "tag_detail_tv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        TvShow tvShow = getIntent().getParcelableExtra(TAG_DETAIL_TV);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvName, tvOriginalName, tvVoteAverage, tvOverview, tvFirstAirDate, tvOriLang;
        ImageView imgPoster, imgBackground;
        tvName = findViewById(R.id.tv_title);
        tvOriginalName = findViewById(R.id.tv_original_name);
        tvVoteAverage = findViewById(R.id.tv_vote);
        tvOverview = findViewById(R.id.tv_overview);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date);
        tvOriLang = findViewById(R.id.tv_original_language);
        imgPoster = findViewById(R.id.image_poster);
        imgBackground = findViewById(R.id.expanded_image);
        if(tvShow!=null){
            Objects.requireNonNull(getSupportActionBar()).setTitle(tvShow.getName());
            tvName.setText(tvShow.getName());
            tvOriginalName.setText(tvShow.getOriginalName());
            tvVoteAverage.setText(tvShow.getVoteAverage());
            tvOverview.setText(tvShow.getOverview());
            tvFirstAirDate.setText(tvShow.getFirstAirDate());
            tvOriLang.setText(tvShow.getOriginalLanguage());
            Glide.with(this)
                    .load(Movie.PATH_IMG+tvShow.getPosterPath())
                    .apply(new RequestOptions().fitCenter())
                    .into(imgPoster);
            Glide.with(this)
                    .load(Movie.PATH_IMG+tvShow.getBackdropPath())
                    .apply(new RequestOptions().centerCrop())
                    .into(imgBackground);
        }
    }
}
