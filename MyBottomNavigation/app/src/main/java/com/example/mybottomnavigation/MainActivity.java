package com.example.mybottomnavigation;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new DashboardFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    fragment = new NotificationsFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_layout,fragment,fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.navigation);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(savedInstanceState==null){
          navView.setSelectedItemId(R.id.navigation_home);
        }
    }
}
