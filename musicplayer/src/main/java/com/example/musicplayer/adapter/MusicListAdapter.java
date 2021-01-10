package com.example.musicplayer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.PlayActivity;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.model.user.MusicInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 当前播放列表的适配器
 */
public class MusicListAdapter extends BaseAdapter {
    private int index=-1;
    private MusicPlayerApplication application;
    private LayoutInflater inflater;
    private List<MusicInfo> data;
    private Context context;
    public MusicListAdapter(Context context, MusicPlayerApplication application) {
        this.context=context;
        this.application=application;
        this.data=application.musicInfos;
        inflater=LayoutInflater.from(context);
    }
    public void setIndex(int i){
        index=i;
    }

    @Override
    public int getCount() {
        if (data==null)return 0;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        if (data==null)return null;
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        final TextView musicname;
        final TextView singername;
        final ImageView delete;
        view = inflater.inflate(R.layout.select, parent, false);
        delete=view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application.musicInfos.remove(position);
                MusicPlayerApplication.serialization(application.appSet);
                MusicListAdapter.this.notifyDataSetChanged();

                Toast.makeText(context,"已移除",Toast.LENGTH_SHORT).show();
            }
        });
        musicname=view.findViewById(R.id.musicname);
        singername=view.findViewById(R.id.singername);
        if (index==position){
            ImageView head_img=view.findViewById(R.id.head_img);
            Glide.with(view)
                    .load(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg())
                    .into(head_img);
            head_img.setVisibility(View.VISIBLE);
            view.setBackgroundColor(Color.parseColor("#F4EFEF"));
        }
        musicname.setText(((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getSongName());
        singername.setText(((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getAuthorName());
        return view;
    }
}
