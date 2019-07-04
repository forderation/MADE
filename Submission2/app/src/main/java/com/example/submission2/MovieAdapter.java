package com.example.submission2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    private ArrayList<Movie> listMovie;
    private Context context;

    MovieAdapter(ArrayList<Movie> listMovie, Context context) {
        this.listMovie = listMovie;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_movie
        ,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = getListMovie().get(i);
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvReleaseDate.setText(movie.getReleaseDate());
        viewHolder.tvOverview.setText(movie.getOverview());
        Glide.with(context)
                .load(Movie.PATH_IMG+movie.getPosterPath())
                .apply(new RequestOptions().fitCenter())
                .into(viewHolder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    private ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvReleaseDate, tvOverview;
        ImageView imgPoster;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_date);
            tvOverview = itemView.findViewById(R.id.tv_item_overview);
            imgPoster = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
