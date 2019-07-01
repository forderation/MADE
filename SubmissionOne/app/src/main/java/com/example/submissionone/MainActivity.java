package com.example.submissionone;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    public final String [] listIdFilms = {"297802","332562","299536","405774","424694","424783","312221"
            ,"293660","12609","166428","450465","399402","400650","428078","568709","375588","557",
            "446807","504172","335983"};
    public ArrayList<Film> listFilms;
    GetFilms getFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listFilms = new ArrayList<>();
        getFilms = new GetFilms();
        getFilms.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(getFilms != null){
            getFilms.cancel(true);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetFilms extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            final String URL = "https://api.themoviedb.org/3/movie/";
            final String API_KEY = "?api_key=b70e6c404b2eaf58839f2b012bf94822";
            HttpHandler sh = new HttpHandler();
            String jsonStr;
            JSONObject jsonObj;
            Film film;
                try {
                    for (String listIdFilm : listIdFilms) {
                        jsonStr = sh.makeServiceCall(URL + listIdFilm + API_KEY);
                        Log.d("DEBUG",URL + listIdFilm + API_KEY);
                        jsonObj = new JSONObject(jsonStr);
                        film = new Film();
                        film.setTitle(jsonObj.getString("original_title"));
                        film.setHomepage(jsonObj.getString("homepage"));
                        film.setOverview(jsonObj.getString("overview"));
                        film.setPoster(jsonObj.getString("poster_path"));
                        film.setRelease(jsonObj.getString("release_date"));
                        film.setVote(jsonObj.getString("vote_average"));
                        film.setBackground(jsonObj.getString("backdrop_path"));
                        listFilms.add(film);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            } 
            FilmAdapter filmAdapter = new FilmAdapter(getApplicationContext());
            filmAdapter.setFilms(listFilms);
            ListView listView = findViewById(R.id.lv_films);
            listView.setAdapter(filmAdapter);
        }
    }
}
