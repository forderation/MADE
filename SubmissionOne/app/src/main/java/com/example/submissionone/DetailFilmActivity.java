package com.example.submissionone;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DetailFilmActivity extends AppCompatActivity {
    public static final String PARCEL_DETAIL_FILM = "parcel_detail_film";
    private Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        film = getIntent().getParcelableExtra(PARCEL_DETAIL_FILM);
        ImageView imgPoster = findViewById(R.id.img_poster);
        ImageView imgBackground = findViewById(R.id.img_background);
        TextView tvVote = findViewById(R.id.txtProgress);
        TextView tvReleaseDate = findViewById(R.id.rv_release_date);
        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvOverview = findViewById(R.id.tv_overview);
        TextView tvHomepage = findViewById(R.id.tv_homepage);
        ProgressBar pbRate = findViewById(R.id.progressBar);
        new DownloadImageTask(imgPoster).execute(Film.URI_IMG+film.getPoster());
        new DownloadImageTask(imgBackground).execute(Film.URI_IMG+film.getBackground());
        //Edit Data First
        String releaseDate = "Release : " + film.getRelease();
        String titleFilm = "Title : " + film.getTitle();
        SpannableString content = new SpannableString(film.getHomepage());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        int rate = (int) Double.parseDouble(film.getVote())*10;
        //Binding Data from POJO
        tvTitle.setText(titleFilm);
        tvVote.setText(film.getVote());
        tvOverview.setText(film.getOverview());
        tvHomepage.setText(content);
        pbRate.setProgress(rate);
        tvReleaseDate.setText(releaseDate);
        tvHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(film.getHomepage()));
                startActivity(browserIntent);
            }
        });
    }
}
