package com.example.harkkatyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GameListAdapter extends ArrayAdapter<Game> {

    private final Context mContext;
    private final int mResource;

    private static class ViewHolder {
        ImageView ivCoverImage;
        TextView tvGameName;
    }

    public GameListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Game> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String gameName = getItem(position).getGameName();
        String imageUrl = getItem(position).getImageUrl();

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.tvGameName = (TextView) convertView.findViewById(R.id.tv_player_name_listview);
            holder.ivCoverImage = (ImageView) convertView.findViewById(R.id.ivCoverImage);

            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Setting up correct name for each game
        holder.tvGameName.setText(gameName);

        Glide.with(mContext)
                .load(imageUrl)
                .override(300, 200)
                .into(holder.ivCoverImage);

        return convertView;
    }
}