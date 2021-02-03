package com.example.musicplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicplayer.R;
import com.example.musicplayer.model.music.singer.Info;
import com.example.musicplayer.model.music.singer.SingerSingleMusic;
import org.jetbrains.annotations.NotNull;

/**
 * @author 章可政
 * @date 2021/1/31 23:35
 */
public class SingerSingleMusicAdapter extends RecyclerView.Adapter {
    private final SingerSingleMusic singerSingleMusic;

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SingerSingleMusicAdapter(SingerSingleMusic singerSingleMusic) {
        this.singerSingleMusic=singerSingleMusic;
    }


    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singer_single_music,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Info infoItem= singerSingleMusic.getData().getInfo().get(position);
        ViewHolder h=(ViewHolder)holder;
        h.songname.setText(infoItem.getFilename().split("-")[1]);
        h.publish.setText(infoItem.getPublishDate());
        holder.itemView.setOnClickListener(v -> {
            if (listener!=null){
                listener.onClick(position);
            }

        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return singerSingleMusic.getData().getInfo().size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView songname;
        TextView publish;
        public ViewHolder (View view)
        {
            super(view);
            songname=view.findViewById(R.id.songname);
            publish=view.findViewById(R.id.publish);
        }
    }

    public interface OnItemClickListener {

        void onClick(int pos);
    }

    }
