package com.example.harkkatyo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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

public class LeaderboardAdapter extends ArrayAdapter<Run> {

    private Context mContext;
    private int mResource;

    private static class ViewHolder {
        TextView placement;
        ImageView trophy;
        TextView playerName;
        TextView time;
        ImageView flag;
    }

    public LeaderboardAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Run> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String placement = getItem(position).getPlacement();
        String trophyUrl = getItem(position).getTrophyUrl();
        String playerName = getItem(position).getPlayer().getPlayerName();
        String time = getItem(position).getTime();
        String flagUrl = "https://www.speedrun.com/images/flags/" + getItem(position).getPlayer().getCountry() + ".png";
        String colorStart = getItem(position).getPlayer().getColorStart();
        String colorEnd = getItem(position).getPlayer().getColorEnd();

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.placement = convertView.findViewById(R.id.tvPlace);
            holder.trophy = convertView.findViewById(R.id.ivTrophy);
            holder.playerName = convertView.findViewById(R.id.tvUsername);
            holder.time = convertView.findViewById(R.id.tvTime);
            holder.flag = convertView.findViewById(R.id.ivFlag);

            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Setting up correct info for each run
        holder.placement.setText(placement);
        holder.playerName.setText(playerName);
        holder.time.setText(time);

        // Setting up the color (gradient) for each player
        holder.playerName.setTextColor(Color.parseColor(colorStart));
        Shader textShader = new LinearGradient(0, 0, holder.playerName.getPaint().measureText(holder.playerName.getText().toString()), holder.playerName.getTextSize(),
                new int[]{Color.parseColor(colorStart), Color.parseColor(colorEnd)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        holder.playerName.getPaint().setShader(textShader);

        // Condition for which background the run should have
        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#171717"));
        }
        else {
            convertView.setBackgroundColor(Color.parseColor("#101010"));
        }

        if (trophyUrl == null) {
            holder.trophy.setVisibility(View.GONE);
            holder.placement.setVisibility(View.VISIBLE);
        }
        else {
            holder.trophy.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(trophyUrl)
                    .override(300, 200)
                    .into(holder.trophy);
            holder.placement.setVisibility(View.INVISIBLE);
        }

        if (flagUrl.compareTo("https://www.speedrun.com/images/flags/default.png") != 0) {
            Glide.with(mContext)
                    .load(flagUrl)
                    .override(300, 200)
                    .into(holder.flag);

            holder.playerName.setText("" + playerName);
        }

        return convertView;
    }
}