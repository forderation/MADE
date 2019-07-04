package com.example.submission2;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.Objects;

public class MovieDetailActivity extends AppCompatActivity {
    private Menu menu;
    private Movie movie;
    public static final String TAG_DETAIL_MOVIE = "tag_detail_movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvTitle, tvOverview, tvReleaseDate, tvAdult, tvStatus,
                tvHomepage, tvVoteAverage, tvTagline, tvLanguage;

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvAdult = findViewById(R.id.tv_adult);
        tvStatus = findViewById(R.id.tv_status);
        tvHomepage = findViewById(R.id.tv_homepage);
        tvVoteAverage = findViewById(R.id.tv_vote);
        tvTagline = findViewById(R.id.tv_tagline);
        tvLanguage = findViewById(R.id.tv_original_language);
        ImageView imagePoster = findViewById(R.id.image_poster);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        movie = getIntent().getParcelableExtra(TAG_DETAIL_MOVIE);
        ImageView expandedImage = findViewById(R.id.expanded_image);

        if(movie!=null){
            Objects.requireNonNull(getSupportActionBar()).setTitle(movie.getOriginalTitle());
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvAdult.setText(movie.getAdult());
            tvStatus.setText(movie.getStatus());
            tvHomepage.setText(movie.getHomepage());
            tvVoteAverage.setText(movie.getVoteAverage());
            tvTagline.setText(movie.getTagline());
            tvLanguage.setText(movie.getOriginalLanguage());
            Glide.with(getApplicationContext())
                    .load(Movie.PATH_IMG+movie.getPosterPath())
                    .apply(new RequestOptions().fitCenter())
                    .into(imagePoster);
            Glide.with(getApplicationContext())
                    .load(Movie.PATH_IMG+movie.getBackdropPath())
                    .apply(new RequestOptions().centerCrop())
                    .into(expandedImage);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resources rcs = getResources();
                Snackbar.make(view, rcs.getString(R.string.visit_url), Snackbar.LENGTH_LONG)
                        .setAction(rcs.getString(R.string.sure_visit), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                assert movie != null;
                                if(movie.getHomepage().compareToIgnoreCase("null")!=0){
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW)
                                            .setData(Uri.parse(movie.getHomepage()));
                                    startActivity(browserIntent);
                                }else{
                                    Snackbar snackbar1 = Snackbar
                                            .make(view,
                                                    getResources().getString(R.string.url_not_found),
                                                    Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            }
                        }).show();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + i == 0) {
                    isShow = true;
                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_info)
        {
            if(movie.getHomepage().compareToIgnoreCase("null")!=0){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(movie.getHomepage()));
                startActivity(browserIntent);
            }else{
                Toast.makeText(this,getResources().getString(R.string.url_not_found),
                        Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        hideOption(R.id.action_info);
        return super.onCreateOptionsMenu(menu);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }
}
