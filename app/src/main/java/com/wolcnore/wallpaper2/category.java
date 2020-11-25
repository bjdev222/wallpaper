package com.wolcnore.wallpaper2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class category extends AppCompatActivity {

    CardView hd,fk,nature,bnw,foodie,love;
private AdView mAdView;
BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        hd=findViewById(R.id.hd);
        fk=findViewById(R.id.fk);
        nature=findViewById(R.id.nature);
        bnw=findViewById(R.id.bnw);
        foodie=findViewById(R.id.foodie);
        love=findViewById(R.id.love);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent i1 = new Intent(category.this, category.class);
                        startActivity(i1);
                        break;

                    case R.id.nav_favorites:
                        Intent i2 = new Intent(category.this, MainActivity.class);
                        startActivity(i2);
                        break;
                    case R.id.nav_search:
                        Intent i3 = new Intent(category.this, search.class);
                        startActivity(i3);

                }
                return true;
            }
        });
        hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(category.this,view.class);
                i1.putExtra("key","hd wallpaper");
                startActivity(i1);

            }
        });
        fk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(category.this,view.class);
                i1.putExtra("key","4k wallpaper");
                startActivity(i1);

            }
        });
        nature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(category.this,view.class);
                i1.putExtra("key","nature wallpaper 4k");
                startActivity(i1);

            }
        });
        bnw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(category.this,view.class);
                i1.putExtra("key","black wallpaper");
                startActivity(i1);

            }
        });
        foodie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(category.this,view.class);
                i1.putExtra("key","food wallpaper");
                startActivity(i1);

            }
        });
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1=new Intent(category.this,view.class);
                i1.putExtra("key","love wallpaper");
                startActivity(i1);

            }
        });

        MobileAds.initialize(this,
                getString(R.string.admob_app_id));

        // Find Banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Display Banner ad
        mAdView.loadAd(adRequest);


    }

    @Override
    public void onBackPressed() {
        ViewDialog alert = new ViewDialog();
        alert.showDialog(category.this, "Are you sure you want to exit? ");
    }
}