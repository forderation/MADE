package com.example.myrecyclerview;

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

public class ListHeroAdapter extends RecyclerView.Adapter<ListHeroAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<Hero> listHero;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_row_hero,viewGroup,false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.tvName.setText(getListHero().get(i).getName());
        categoryViewHolder.tvFrom.setText(getListHero().get(i).getFrom());
        Glide.with(context)
                .load(getListHero().get(i).getPhoto())
                .apply(new RequestOptions().override(55,55))
                .into(categoryViewHolder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return getListHero().size();
    }

    void setContext(Context context) {
        this.context = context;
    }

    void setListHero(ArrayList<Hero> listHero) {
        this.listHero = listHero;
    }

    private ArrayList<Hero> getListHero() {
        return listHero;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvFrom;
        ImageView imgPhoto;
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvFrom = itemView.findViewById(R.id.tv_item_from);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
