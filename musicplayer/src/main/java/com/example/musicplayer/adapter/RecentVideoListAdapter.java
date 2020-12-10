package com.example.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.model.mv.MV;

import java.util.List;

/**
 * @author 章可政
 * @date 2020/11/10 19:47
 */
public class RecentVideoListAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<MV> data;
    private final Context context;
    public RecentVideoListAdapter(List<MV> data, Context context) {
        this.data = data;
        this.context=context;
        inflater=LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view=inflater.inflate(R.layout.recent_video_list_item,parent,false);
        MV item = (MV) getItem(position);
        if (data==null){
            return view;
        }
        if (item==null){
            return view;
        }
        ImageView recent_video_list_item_image=view.findViewById(R.id.recent_video_list_item_image);
        TextView recent_video_list_item_videoName=view.findViewById(R.id.recent_video_list_item_videoName);
        TextView recent_video_list_item_singerName=view.findViewById(R.id.recent_video_list_item_singerName);
        String imgurl = item.getMvDetail().getData().getInfo().getImgurl();
        imgurl=imgurl.replace("{size}","400");
        Glide.with(context).load(imgurl).into(recent_video_list_item_image);
        recent_video_list_item_videoName.setText(item.getMvDetail().getData().getInfo().getAudioInfo().getSongname());
        recent_video_list_item_singerName.setText(item.getMvDetail().getData().getInfo().getAudioInfo().getSingername());
        return view;
    }
}
