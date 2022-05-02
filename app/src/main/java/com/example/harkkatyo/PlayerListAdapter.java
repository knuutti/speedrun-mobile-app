/* PlayerListAdapter.java

This code file is for displaying players in a ListView in a desired way (extending ArrayAdapter)

*/

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

public class PlayerListAdapter extends ArrayAdapter<Player> {

    private final Context mContext;
    private final int mResource;

    private static class ViewHolder {
        TextView tvPlayerName;
    }

    public PlayerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Player> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String playerName = getItem(position).getPlayerName();


        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new PlayerListAdapter.ViewHolder();
            holder.tvPlayerName = (TextView) convertView.findViewById(R.id.tv_player_name_listview);


            convertView.setTag(holder);

        }
        else {
            holder = (PlayerListAdapter.ViewHolder) convertView.getTag();
        }

        // Setting up correct name for each game
        holder.tvPlayerName.setText(playerName);


        return convertView;
    }
}
