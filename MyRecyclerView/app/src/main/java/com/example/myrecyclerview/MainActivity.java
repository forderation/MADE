package com.example.myrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCategory;
    ArrayList<Hero> list = new ArrayList<>();
    private String STATE_TITLE = "state_title";
    private String STATE_LIST = "state_list";
    private String STATE_MODE = "state_mode";
    private String title;
    private int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvCategory = findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);
        list = new ArrayList<>();
        if(savedInstanceState==null){
            setActionBarTitle("Mode List");
            list.addAll(HeroesData.getListData());
            showRecyclerList();
            mode = R.id.action_list;
        }else{
            String stateTitle = savedInstanceState.getString(STATE_TITLE);
            ArrayList<Hero> stateList = savedInstanceState.getParcelableArrayList(STATE_LIST);
            list.addAll(stateList!=null? stateList:HeroesData.getListData());
            setActionBarTitle(stateTitle);
            setMode(savedInstanceState.getInt(STATE_MODE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE,title);
        outState.putParcelableArrayList(STATE_LIST,list);
        outState.putInt(STATE_MODE,mode);
    }

    void showRecyclerList(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        ListHeroAdapter listHeroAdapter = new ListHeroAdapter();
        listHeroAdapter.setContext(this);
        listHeroAdapter.setListHero(list);
        rvCategory.setAdapter(listHeroAdapter);
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedHero(list.get(position));
            }
        });
    }

    void showRecyclerGrid(){
        rvCategory.setLayoutManager(new GridLayoutManager(this,2));
        GridHeroAdapter gridHeroAdapter = new GridHeroAdapter(this);
        gridHeroAdapter.setListHero(list);
        rvCategory.setAdapter(gridHeroAdapter);
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedHero(list.get(position));
            }
        });
    }

    void showRecyclerCardView(){
        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        CardViewHeroAdapter cardViewHeroAdapter = new CardViewHeroAdapter(this);
        cardViewHeroAdapter.setListHero(list);
        rvCategory.setAdapter(cardViewHeroAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode){
            case R.id.action_list:
                showRecyclerList();
                setActionBarTitle("Mode List");
                break;
            case R.id.action_grid:
                showRecyclerGrid();
                setActionBarTitle("Mode Grid");
                break;
            case R.id.action_cardview:
                showRecyclerCardView();
                setActionBarTitle("Mode Card View");
                break;
        }
        mode = selectedMode;
    }

    void setActionBarTitle(String title){
        this.title = title;
        if(title != null && getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    private void showSelectedHero(Hero hero){
        Toast.makeText(this, "Kamu memilih "+hero.getName(), Toast.LENGTH_SHORT).show();
    }
}
