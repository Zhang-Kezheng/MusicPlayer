package com.example.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.model.user.MusicInfo;

import java.util.List;

/**
 * @author 章可政
 * @date 2020/11/3 22:45
 */
public class RecentSingleMusicAdapter extends BaseAdapter {
    private int index=-1;
    private final LayoutInflater inflater;
    private final List<MusicInfo> data;

    public RecentSingleMusicAdapter(Context context, List<MusicInfo> data) {
        this.data=data;
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
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        if (index==position){
            view=inflater.inflate(R.layout.recent_single_music_list_item_selected,parent,false);
            ImageView singer_head=view.findViewById(R.id.singer_head);
            TextView file_name=view.findViewById(R.id.file_name);
            Glide.with(view)
                    .load(((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getAuthors().get(0).getAvatar())
                    .into(singer_head);
            file_name.setText(((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getSongName()+"-"+((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getAuthorName());
        }else {
            final TextView songname;
            final TextView singername;
            view = inflater.inflate(R.layout.recent_single_music_list_item, parent, false);
            songname=view.findViewById(R.id.songname);
            singername=view.findViewById(R.id.singername);
            songname.setText(((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getSongName());
            singername.setText(((MusicInfo) getItem(position)).getMusicPlayUrlData().getData().getAuthorName());
        }
        return view;
    }
}
