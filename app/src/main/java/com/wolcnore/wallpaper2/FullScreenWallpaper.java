package com.wolcnore.wallpaper2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import at.markushi.ui.CircleButton;

public class FullScreenWallpaper extends AppCompatActivity {
    String originalUrl="";
    PhotoView photoView;

    CircleButton cb;
    private InterstitialAd interstitial;

private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_wallpaper);
        cb=findViewById(R.id.set_Wallpaper);

        Toast.makeText(this, "HD Wallpaper may take more time to load..", Toast.LENGTH_LONG).show();


        MobileAds.initialize(this,
                getString(R.string.admob_app_id));


        // Find Banner ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Display Banner ad
        mAdView.loadAd(adRequest);


      RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.animation)
                .error(R.drawable.loaderr)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();


        getSupportActionBar().hide();
        Intent intent =  getIntent();
        originalUrl = intent.getStringExtra("originalUrl");
        photoView = findViewById(R.id.photoView);
        Glide.with(this).load(originalUrl)
                .apply(options)
                .into(photoView);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWallpaperEvent(view);
            }
        });

        //INterstitial google
        AdRequest adIRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad Activity
        interstitial = new InterstitialAd(this);

        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        // Interstitial Ad load Request
        interstitial.loadAd(adIRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener()
        {
            public void onAdLoaded()
            {
                // Call displayInterstitial() function when the Ad loads
             //   displayInterstitial();
            }
        });

    }
    public void displayInterstitial()
    {
        // If Interstitial Ads are loaded then show else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    public void SetWallpaperEvent(View view) {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap bitmap  = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
            displayInterstitial();
           /* if(interstitialAd != null && interstitialAd.isAdLoaded())
            {
                interstitialAd.show();
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setLockScreen(View view) {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
            try {
                wallpaperManager.setBitmap(bitmap, null, true, wallpaperManager.FLAG_LOCK);
                Toast.makeText(this, "Lock screen wallpaper Set", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void DownloadWallpaperEvent(View view) {

        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(originalUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading start..Check Your Notification...", Toast.LENGTH_LONG).show();
        displayInterstitial();
       /* if(interstitialAd != null && interstitialAd.isAdLoaded())
        {
            interstitialAd.show();
        }*/

    }

    public void ShareWallpaper(View view){

        Bitmap bitmap=getBitmapFromView(photoView);
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try {

            File file=new File(this.getExternalCacheDir(),"wallpaper.jpg");
            FileOutputStream fout=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fout);
            fout.flush();
            fout.close();
            file.setReadable(true,false);
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, "Download this types of Amazing wallpaper from here" +
                    " https://play.google.com/store/apps/details?id=" + FullScreenWallpaper.this.getPackageName());
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

            intent.setType("*/*");

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Toast.makeText(this, "Image", Toast.LENGTH_SHORT).show();
            startActivity(Intent.createChooser(intent,"Share Image"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public void showDialog(){
        final Dialog dialog = new Dialog(FullScreenWallpaper.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.choose);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView home = (TextView) dialog.findViewById(R.id.homeWall);
        TextView lock = (TextView) dialog.findViewById(R.id.lockWall);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                SetWallpaperEvent(v);
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_yes);
        lock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                    setLockScreen(v);
                }
            }
        });

        dialog.show();
        dialog.setCancelable(true);
    }




    @SuppressLint("ResourceAsColor")
    private Bitmap getBitmapFromView(View view){
        Bitmap returnBitmap=Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(returnBitmap);
        Drawable bgDrawable=view.getBackground();
        if(bgDrawable !=null){
            bgDrawable.draw(canvas);
        }else{
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return returnBitmap;
    }


}