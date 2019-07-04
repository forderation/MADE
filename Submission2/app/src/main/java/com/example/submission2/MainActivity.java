package com.example.submission2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private ProgressBar progressBar;
    private Snackbar snackbar;
    private ArrayList<Movie> listMovies;
    public final String [] listIdMovies = {"301528","429617","287947","399579","157433","521029","566555"
            ,"299537","420817","320288","299534","486131","299536","329996","479455","553100","537915",
            "375588","245891","166428"};
    private static final String TAG_LIST_MOVIES = "TAG_LIST_MOVIES";
    private Fragment fragment;
    private BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        navigationView = findViewById(R.id.navigation);
        boolean isListMoviesEmpty = false;
        if(savedInstanceState == null){
            isListMoviesEmpty = true;
        }else{
            ArrayList<Movie> listMoviesSaved =
                    savedInstanceState.getParcelableArrayList(TAG_LIST_MOVIES);
            if(listMoviesSaved==null){
                isListMoviesEmpty = true;
            }else{
                listMovies = new ArrayList<>();
                listMovies.addAll(listMoviesSaved);
                showMovieFragment();
            }
        }
        if(isListMoviesEmpty){
            new GetDataMovies().execute();
        }
        navigationView.setSelectedItemId(R.id.movie_nav);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void showMovieFragment(){
        if(!(fragment instanceof MovieFragment)){
            fragment = new MovieFragment(listMovies);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_layout,fragment,
                            fragment.getClass().getSimpleName())
                    .commit();
        }
        Objects.requireNonNull(getSupportActionBar())
                .setTitle(getResources().getString(R.string.bar_title_movie));
    }

    private void showTvShowFragment(){
        if(!(fragment instanceof TvShowFragment)){
            fragment = new TvShowFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_layout,fragment,
                            fragment.getClass().getSimpleName())
                    .commit();
        }
        Objects.requireNonNull(getSupportActionBar())
                .setTitle(getResources().getString(R.string.bar_title_tv));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.movie_nav:
                    showMovieFragment();
                    return true;
                case R.id.tv_show_nav:
                    showTvShowFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_LIST_MOVIES,listMovies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        navigationView.setSelectedItemId(R.id.movie_nav);
        Toast.makeText(this,getResources().getString(R.string.instruction_restart),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if(item.getItemId()==R.id.refresh_settings){
            Intent mIntent = getIntent();
            finish();
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    class GetDataMovies extends AsyncTask<Void,Integer,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            snackbar = Snackbar.make(findViewById(R.id.container),
                    getResources().getString(R.string.loading_data), Snackbar.LENGTH_INDEFINITE);
            snackbar.getView() .setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        }

        @Override
        protected Void doInBackground(Void... avoid) {
            String currentLocale = Locale.getDefault().getLanguage();
            if(currentLocale.compareToIgnoreCase("in")==0){
                currentLocale = "id";
            }
            final String URL = "https://api.themoviedb.org/3/movie/";
            final String API_KEY = "?api_key=" + BuildConfig.API_movie_DB +
                    "&language=" + currentLocale;
            HttpHandler sh = new HttpHandler();
            String jsonStr;
            JSONObject jsonObj;
            Movie movie;
            listMovies = new ArrayList<>();
            int valuePerProgress = progressBar.getMax() / listIdMovies.length;
            try {
                for (String idMovie : listIdMovies) {
                    progressBar.setProgress(progressBar.getProgress()+valuePerProgress);
                    jsonStr = sh.makeServiceCall(URL + idMovie + API_KEY);
                    jsonObj = new JSONObject(jsonStr);
                    movie = new Movie();
                    movie.setAdult(jsonObj.getString("adult"));
                    movie.setBackdropPath(jsonObj.getString("backdrop_path"));
                    movie.setHomepage(jsonObj.getString("homepage"));
                    movie.setOriginalTitle(jsonObj.getString("original_title"));
                    movie.setOverview(jsonObj.getString("overview"));
                    movie.setTagline(jsonObj.getString("tagline"));
                    movie.setVoteAverage(jsonObj.getString("vote_average"));
                    movie.setPosterPath(jsonObj.getString("poster_path"));
                    movie.setStatus(jsonObj.getString("status"));
                    movie.setReleaseDate(jsonObj.getString("release_date"));
                    movie.setOriginalLanguage(jsonObj.getString("original_language"));
                    listMovies.add(movie);
                }
                progressBar.setProgress(100);
            } catch (final JSONException e) {
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                };
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            snackbar.dismiss();
            showMovieFragment();
        }
    }
}
