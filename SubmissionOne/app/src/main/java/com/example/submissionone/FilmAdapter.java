package com.example.submissionone;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Film> films;
    FilmAdapter(Context context){
        this.context = context;
        films = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d("DEBUG","Position: " + position);
        return this.films.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if(v == null)
            v = LayoutInflater.from(context).inflate(R.layout.item_film,
                    parent, false);
        ViewHolder viewHolder = new ViewHolder(v,parent,position);
        Film film = (Film) getItem(position);
        viewHolder.bind(film);
        return v;
    }

    void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    private class ViewHolder {
        private TextView tvTitle, tvReleaseDate, tvOverview;
        private ImageView imgPoster;
        private Button btnReadMore;

        ViewHolder(View view, final ViewGroup parent, final int position){
            tvTitle = view.findViewById(R.id.tv_title);
            tvReleaseDate = view.findViewById(R.id.tv_release_date);
            tvOverview = view.findViewById(R.id.tv_overview);
            imgPoster = view.findViewById(R.id.img_poster);
            btnReadMore = view.findViewById(R.id.btn_read_more);
            btnReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailFilmIntent = new Intent(parent.getContext(),
                            DetailFilmActivity.class);
                    detailFilmIntent.putExtra(DetailFilmActivity.PARCEL_DETAIL_FILM,
                            films.get(position));
                    parent.getContext().startActivity(detailFilmIntent);
                }
            });
        }

        void bind(Film film){
            tvTitle.setText(film.getTitle());
            tvReleaseDate.setText(film.getRelease());
            tvOverview.setText(film.getOverview());
            new DownloadImageTask(imgPoster)
                    .execute(Film.URI_IMG+film.getPoster());
        }
    }
}
