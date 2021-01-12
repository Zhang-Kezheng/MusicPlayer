package com.example.musicplayer.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.musicplayer.R;
import com.example.musicplayer.model.music.tips.RecordData;
import com.example.musicplayer.model.music.tips.SeachTips;

/**
 * @author 章可政
 * @date 2021/1/10 22:35
 */
public class SearchTipListAdapter extends BaseAdapter {
    private SeachTips seachTips;
    private LayoutInflater inflater;
    public SearchTipListAdapter(Context context,SeachTips seachTips) {
        this.seachTips=seachTips;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return seachTips.getData().get(0).getRecordDatas().size();
    }

    @Override
    public Object getItem(int position) {
        return seachTips.getData().get(0).getRecordDatas().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View view=inflater.inflate(R.layout.tip_list_item,null);
        TextView tip=view.findViewById(R.id.tip);
        RecordData item = (RecordData) getItem(position);
        tip.setText(item.getHintInfo());
        return view;
    }
}
