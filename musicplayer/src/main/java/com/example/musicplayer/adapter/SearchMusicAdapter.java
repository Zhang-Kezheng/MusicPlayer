package com.example.musicplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.MVActivity;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.model.music.searchmusicinfo.List;
import com.example.musicplayer.model.music.searchmusicinfo.SearchMusicInfoData;
import com.example.musicplayer.model.mv.MV;
import com.example.musicplayer.model.mv.detail.MVDetail;
import com.example.musicplayer.model.mv.playurl.MVModel;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.util.HttpUtil;
import com.google.gson.Gson;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.musicplayer.activity.PlayActivity.saveMV;
import static com.example.musicplayer.commons.MusicPlayerApplication.*;

/**
 * @author 章可政
 * @date 2021/1/9 21:51
 */
public class SearchMusicAdapter {
    private int index=-1;
    private LayoutInflater inflater;
    private SearchMusicInfoData data;
    private MusicPlayerApplication application;
    private   Context context;
    private final MV mvData=new MV();
    private String mvHash;
    private MusicInfo musicInfo;
    public SearchMusicAdapter(MusicPlayerApplication application,Context context, SearchMusicInfoData data) {
        this.application=application;
        this.data = data;
        inflater=LayoutInflater.from(context);
        this.context=context;
    }

    public void setIndex(int i){
        index=i;
    }

    public int getCount() {
        if (data.getData()==null)return 0;
        return data.getData().getLists().size();
    }

    public Object getItem(int position) {
        if (data.getData()==null)return null;
        return data.getData().getLists().get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position) {
        final View view;
        String fileName=((List) getItem(position)).getFileName();
        String songName = fileName.split("-")[1];
        if (songName.contains("<em>")){
            songName = songName.replace("<em>", "<font color=#0000FF>");
            songName = songName.replace("</em>", "</font>");
        }
        String singerName = ((List) getItem(position)).getSingerName();
        if (singerName.contains("<em>")){
            singerName = singerName.replace("<em>", "<font color=#0000FF>");
            singerName = singerName.replace("</em>", "</font>");
        }
        if (index==position){
            view=inflater.inflate(R.layout.recent_single_music_list_item_selected,null,false);
            TextView file_name=view.findViewById(R.id.file_name);
            file_name.setText(Html.fromHtml(songName)+"-"+Html.fromHtml(singerName));
        }else {
            final TextView songname;
            final TextView singername;
            final ImageView mv;
            view = inflater.inflate(R.layout.search_music_list_item, null, false);
            mv=view.findViewById(R.id.mv);
            if(((List) getItem(position)).getMvHash().equals("")){
                mv.setVisibility(View.GONE);
            }
            songname=view.findViewById(R.id.songname);
            singername=view.findViewById(R.id.singername);
            songname.setText(Html.fromHtml(songName));
            String albumName=((List) getItem(position)).getAlbumName();
            if (!"".equals(albumName)){
                albumName="-《"+albumName+"》";
            }
            singername.setText(Html.fromHtml(singerName+albumName));
                    mv.setOnClickListener(v -> {
            List item = (List)getItem(position);
            mvHash=item.getMvHash();
            String key= DigestUtils.md5Hex(mvHash+ "kugoumvcloud");
            URL url= null;
            try {
                url = new URL("http://trackermv.kugou.com/interface/index/cmd=100&hash="+mvHash +
                        "&key="+key+"&pid=6&ext=mp4&ismp3=0");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpUtil.sendGetRequest(url,handler,MV_PLAY_URL);
        });
        }
        return view;
    }
    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            Gson gson=new Gson();
            switch (msg.what){
                case MV_PLAY_URL:
                    String data = bundle.getString("data");
                    MVModel mvModel = gson.fromJson(data, MVModel.class);
                    mvData.setMvModel(mvModel);
                    URL url= null;
                    try {
                        url = new URL("http://mobilecdn.kugou.com/api/v3/mv/detail?area_code=1&plat=0&mvhash="+mvHash+"&with_res_tag=1");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.sendGetRequest(url,handler,MV_DETAIL);
                    break;
                case MV_DETAIL:
                    data = bundle.getString("data");
                    data=data.replace("<!--KG_TAG_RES_START-->","");
                    data=data.replace("<!--KG_TAG_RES_END-->","");
                    MVDetail mvDetail = gson.fromJson(data, MVDetail.class);
                    mvData.setMvDetail(mvDetail);
                    saveMV(mvData,application);
                    Intent intent=new Intent(context, MVActivity.class);
                    intent.putExtra("mv",mvData);
                    context.startActivity(intent);
                    break;
            }
        }
    };

}

