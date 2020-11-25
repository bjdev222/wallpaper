package com.wolcnore.wallpaper2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperViewHolder> {

    RequestOptions options;
    private Context context;
    private List<WallpaperModel> wallpaperModelList;




    public WallpaperAdapter(Context context, List<WallpaperModel> wallpaperModelList) {
        this.context = context;
        this.wallpaperModelList = wallpaperModelList;
    }

    @NonNull
    @Override
    public WallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
            return new WallpaperViewHolder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperViewHolder holder, final int position) {

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.animation)
                .error(R.drawable.loaderr)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        if(position==4){
        }


        Glide.with(context).load(wallpaperModelList.get(position).getMediumUrl()).apply(options).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,FullScreenWallpaper.class)
                        .putExtra("originalUrl",wallpaperModelList.get(position).getOriginalUrl()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return wallpaperModelList.size();
    }
}

class WallpaperViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    public WallpaperViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.imageViewItem);
    }

}
