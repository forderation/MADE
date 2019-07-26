package com.example.myasynctaskloader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    public void setmData(ArrayList<WeatherItems> mData) {
        this.mData.clear();
        this.mData.addAll(mData);
        notifyDataSetChanged();
    }

    public void addItem(final WeatherItems item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    private ArrayList<WeatherItems> mData = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textViewNamaKota.setText(
                mData.get(i).getName()
        );
        viewHolder.textViewTemperature.setText(
                mData.get(i).getTemperature()
        );
        viewHolder.textViewDescription.setText(
                mData.get(i).getDescription()
        );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNamaKota;
        TextView textViewTemperature;
        TextView textViewDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.textDesc);
            textViewNamaKota = itemView.findViewById(R.id.textKota);
            textViewTemperature = itemView.findViewById(R.id.textTemp);
        }
    }
}
