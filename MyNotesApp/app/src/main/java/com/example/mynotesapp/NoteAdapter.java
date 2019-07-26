package com.example.mynotesapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<Note> listNotes = new ArrayList<>();
    private Activity activity;
    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvTitle.setText(listNotes.get(i).getTitle());
        viewHolder.tvDate.setText(listNotes.get(i).getDate());
        viewHolder.tvDesc.setText(listNotes.get(i).getDescription());
        viewHolder.cvNote.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity,NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION,i);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE,listNotes.get(i));
                activity.startActivityForResult(intent,NoteAddUpdateActivity.REQUEST_UPDATE);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public ArrayList<Note> getListNotes() {
        return listNotes;
    }

    public void setListNotes(ArrayList<Note> listNotes) {
        if(listNotes.size()>0){
            this.listNotes.clear();
        }
        this.listNotes.addAll(listNotes);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        this.listNotes.add(note);
        notifyItemInserted(listNotes.size() - 1);
    }

    public void updateItem(int position, Note note) {
        this.listNotes.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position) {
        this.listNotes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,listNotes.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDesc, tvDate;
        final CardView cvNote;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDesc = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }
    }
}
