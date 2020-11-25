package com.wolcnore.wallpaper2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TabActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:

                        Intent i=new Intent(TabActivity.this,MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_favorites:
                        Toast.makeText(TabActivity.this, "Favourite", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_search:
                        Toast.makeText(TabActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        break;

                }
                return  true;
            }
        });
    }
}