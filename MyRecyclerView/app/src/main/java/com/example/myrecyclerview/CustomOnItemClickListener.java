package com.example.myrecyclerview;

import android.view.View;

public class CustomOnItemClickListener implements View.OnClickListener {
    int position;
    private OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);
    }
    public interface OnItemClickCallback{
        void onItemClicked(View view, int position);
    }
}
