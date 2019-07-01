package com.example.mylistview;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] dataName;
    private String[] dataDesciption;
    private TypedArray dataPhoto;
    private HeroAdapter adapter;
    private ArrayList<Hero> heroes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new HeroAdapter(this);
        ListView listView = findViewById(R.id.lv_list);
        listView.setAdapter(adapter);
        prepare();
        addItem();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, heroes.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepare(){
        dataName = getResources().getStringArray(R.array.data_name);
        dataDesciption = getResources().getStringArray(R.array.data_description);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo);
    }

    private void addItem(){
        heroes = new ArrayList<>();
        for (int i=0; i<dataName.length;i++){
            Hero hero = new Hero();
            hero.setPhoto(dataPhoto.getResourceId(i,-1));
            hero.setName(dataName[i]);
            hero.setDescription(dataDesciption[i]);
            heroes.add(hero);
        }
        adapter.setHeroes(heroes);
    }
}
