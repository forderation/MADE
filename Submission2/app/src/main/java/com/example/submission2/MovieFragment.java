package com.example.submission2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    public static final String TAG_LAYOUT = "tag_layout";
    public static final String TAG_LIST = "tag_list";
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Movie> listMovie;

    public MovieFragment(){
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MovieFragment(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState==null){
            this.linearLayoutManager = new LinearLayoutManager(getActivity());
        }else {
            Parcelable state = savedInstanceState.getParcelable(TAG_LAYOUT);
            this.linearLayoutManager.onRestoreInstanceState(state);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_movie);
        MovieAdapter movieAdapter = new MovieAdapter(listMovie,getActivity());
        this.linearLayoutManager = new LinearLayoutManager(getActivity());
        if(savedInstanceState!=null) {
            movieAdapter.setListMovie(savedInstanceState.<Movie>getParcelableArrayList(TAG_LIST));
            Parcelable state = savedInstanceState.getParcelable(TAG_LAYOUT);
            this.linearLayoutManager.onRestoreInstanceState(state);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("fragment","on detach");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TAG_LAYOUT,this.linearLayoutManager.onSaveInstanceState());
        outState.putParcelableArrayList(TAG_LIST,listMovie);
    }
}
