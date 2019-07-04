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

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.viewHolder> {
    private ArrayList<TvShow> tvShowArrayList = new ArrayList<>();
    private Context context;

    private ArrayList<TvShow> getTvShowArrayList() {
        return tvShowArrayList;
    }

    void setTvShowArrayList(ArrayList<TvShow> tvShowArrayList) {
        this.tvShowArrayList.addAll(tvShowArrayList);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_tv,
                viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        TvShow tvShow = getTvShowArrayList().get(i);
        viewHolder.tvTitleTv.setText(tvShow.getName());
        Glide.with(context)
                .load(Movie.PATH_IMG+tvShow.getPosterPath())
                .apply(new RequestOptions().fitCenter())
                .into(viewHolder.imgPosterTv);
    }

    @Override
    public int getItemCount() {
        return getTvShowArrayList().size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    class viewHolder extends RecyclerView.ViewHolder {

        TextView tvTitleTv;
        ImageView imgPosterTv;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleTv = itemView.findViewById(R.id.tv_item_title);
            imgPosterTv = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
