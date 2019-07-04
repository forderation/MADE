package com.example.submission2;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private ArrayList<TvShow> listTvShows =  new ArrayList<>();
    public static final String TAG_ARRAY_TV = "tag_array_tv";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.rv_tv_show);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getActivity(),2);
        if(savedInstanceState==null){
            new getDataTvShow().execute();
        }else{
            listTvShows.addAll(Objects.requireNonNull(
                    savedInstanceState.<TvShow>getParcelableArrayList(TAG_ARRAY_TV)));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_ARRAY_TV,listTvShows);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    class getDataTvShow extends AsyncTask<Void, Integer, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String currentLocale = Locale.getDefault().getLanguage();
            if(currentLocale.compareToIgnoreCase("in")==0){
                currentLocale = "id";
            }
            final String URI_TV = "https://api.themoviedb.org/3/tv/popular?api_key="
                    + BuildConfig.API_movie_DB + "&language=" + currentLocale + "&page=1";
            HttpHandler httpHandler = new HttpHandler();
            String jsonString;
            JSONObject jsonObject;
            JSONArray results;
            jsonString = httpHandler.makeServiceCall(URI_TV);
            try {
                jsonObject = new JSONObject(jsonString);
                results = jsonObject.getJSONArray("results");
                int valueProgress = progressBar.getMax() / results.length();
                for(int i = 0; i < results.length(); i++){
                    JSONObject tvObject = results.getJSONObject(i);
                    Log.d("tv_object",tvObject.getString("name"));
                    TvShow tvShow = new TvShow();
                    tvShow.setBackdropPath(tvObject.getString("backdrop_path"));
                    tvShow.setName(tvObject.getString("name"));
                    tvShow.setOriginalLanguage(tvObject.getString("original_language"));
                    tvShow.setOverview(tvObject.getString("overview"));
                    tvShow.setPosterPath(tvObject.getString("poster_path"));
                    tvShow.setVoteAverage(tvObject.getString("vote_average"));
                    tvShow.setFirstAirDate(tvObject.getString("first_air_date"));
                    tvShow.setOriginalName(tvObject.getString("original_name"));
                    listTvShows.add(tvShow);
                    progressBar.setProgress(progressBar.getProgress()+valueProgress);
                }
            } catch (final JSONException e) {
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                };
            }
            progressBar.setProgress(100);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.INVISIBLE);
            TvShowAdapter tvShowAdapter = new TvShowAdapter();
            tvShowAdapter.setTvShowArrayList(listTvShows);
            tvShowAdapter.setContext(getActivity());
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(tvShowAdapter);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getContext(), TvDetailActivity.class);
                    intent.putExtra(TvDetailActivity.TAG_DETAIL_TV,listTvShows.get(position));
                    startActivity(intent);
                }
            });
        }
    }
}
