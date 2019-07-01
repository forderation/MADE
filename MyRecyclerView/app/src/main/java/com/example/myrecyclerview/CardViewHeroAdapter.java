package com.example.myrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CardViewHeroAdapter extends RecyclerView.Adapter<CardViewHeroAdapter.CardViewHolder> {
    private Context context;
    private ArrayList<Hero> listHero;

    public CardViewHeroAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cardview_hero,viewGroup,
                false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewHolder cardViewHolder, int i) {
        cardViewHolder.tvName.setText(getListHero().get(i).getName());
        cardViewHolder.tvFrom.setText(getListHero().get(i).getFrom());
        Glide.with(context)
                .load(getListHero().get(i).getPhoto())
                .apply(new RequestOptions().override(350,550))
                .into(cardViewHolder.imgPhoto);
        cardViewHolder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        Toast.makeText(context,"Favorite: " + getListHero().get(position).getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
        );
        cardViewHolder.btnShare.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context,"Share: " + getListHero().get(position).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListHero().size();
    }

    public ArrayList<Hero> getListHero() {
        return listHero;
    }

    public void setListHero(ArrayList<Hero> listHero) {
        this.listHero = listHero;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvFrom;
        ImageView imgPhoto;
        Button btnFavorite, btnShare;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvFrom = itemView.findViewById(R.id.tv_item_from);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            btnFavorite = itemView.findViewById(R.id.btn_set_share);
            btnShare = itemView.findViewById(R.id.btn_set_favorite);
        }
    }
}
