package com.example.submission2;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ProgressBar progressBar;
    Snackbar snackbar;
    private ArrayList<Movie> listMovies;
    public final String [] listIdMovies = {"297802","332562","299536","405774","424694","424783","312221"
            ,"293660","12609","166428","450465","399402","400650","428078","568709","375588","557",
            "446807","504172","335983"};
    private static final String TAG_LIST_MOVIES = "TAG_LIST_MOVIES";
    Fragment movieFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        BottomNavigationView navigationView = findViewById(R.id.navigation);
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onCreate(savedInstanceState);
    }

    private void showMovieFragment(){
        if(!(movieFragment instanceof MovieFragment)){
            movieFragment = new MovieFragment(listMovies);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_layout,movieFragment,
                            movieFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    private void showTvShowFragment(){
        if(!(movieFragment instanceof TvShowFragment)){
            movieFragment = new TvShowFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_layout,movieFragment,
                            movieFragment.getClass().getSimpleName())
                    .commit();
        }
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_LIST_MOVIES,listMovies);
    }

    @Override
    public void onClick(View v) {

    }

    @SuppressLint("StaticFieldLeak")
    class GetDataMovies extends AsyncTask<Void,Integer,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            snackbar = Snackbar.make(findViewById(R.id.container),
                    "Please wait load data", Snackbar.LENGTH_INDEFINITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            snackbar.show();
        }

        @Override
        protected Void doInBackground(Void... avoid) {
            final String URL = "https://api.themoviedb.org/3/movie/";
            final String API_KEY = "?api_key=" + BuildConfig.API_movie_DB;
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
