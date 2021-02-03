package com.example.musicplayer.activity;

import android.content.Intent;
import android.os.*;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.adapter.SingerSingleMusicAdapter;
import com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData;
import com.example.musicplayer.model.music.singer.SingerSingleMusic;
import com.example.musicplayer.model.music.singer.singerinfo.SingerInfo;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.util.HttpUtil;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;

public class SingerActivity extends BaseActivity {

    private String singerID;

    private ImageView singer_image;

    private TextView singername;


    private ViewPager singer_pager;

    private List<View> views;

    private View singer_info_view;

    private View singer_single_music_view;

    private TextView info;

    private RecyclerView singer_single_music_list;

    private PageAdapter singer_page_adapter;
    @Override
    public void setContentLayout() {
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_singer);
        Intent intent = getIntent();
        singerID= intent.getStringExtra("singerID");
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        singer_image=findViewById(R.id.singer_image);

        singername=findViewById(R.id.singername);

        singer_pager=findViewById(R.id.singer_pager);

        views=new ArrayList<>();

        initSingerInfoView();

        initSingerSingleMusicView();

        views.add(singer_info_view);

        views.add(singer_single_music_view);

        singer_page_adapter=new PageAdapter(views);

        singer_pager.setAdapter(singer_page_adapter);

        singer_pager.setCurrentItem(0, true);

        Toast.makeText(this,singer_pager.getHeight()+"",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterInitView() {
        URL url= null;
        try {
            url = new URL("http://mobilecdnbj.kugou.com/api/v3/singer/info?singerid="+singerID+"&with_res_tag=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpUtil.sendGetRequest(url,handler,GET_SINGER_INFO);

        try {
            url=new URL("http://mobilecdnbj.kugou.com/api/v3/singer/song?sorttype=2&version=9108&identity=3&plat=0&pagesize=30&singerid="+singerID+"&area_code=1&page=1&with_res_tag=1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpUtil.sendGetRequest(url,handler,GET_SINGER_SINGLE_MUSIC);
    }

    @Override
    public void onClickEvent(View v) {

    }
    Handler handler=new Handler(Looper.getMainLooper()){
        MusicInfo musicInfo = new MusicInfo();
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            String data=bundle.getString("data");
            Gson gson=new Gson();
            switch (msg.what){
                case GET_SINGER_INFO:
                    data=data.replace("<!--KG_TAG_RES_START-->","").replace("<!--KG_TAG_RES_END-->","");
                    SingerInfo singerInfo = gson.fromJson(data, SingerInfo.class);
                    singername.setText(singerInfo.getData().getSingername());
                    Glide.with(SingerActivity.this)
                            .load(singerInfo.getData().getImgurl().replace("{size}","400"))
                            .into(singer_image);
                    info.setText(singerInfo.getData().getIntro());
                    break;
                case GET_SINGER_SINGLE_MUSIC:
                    data=data.replace("<!--KG_TAG_RES_START-->","").replace("<!--KG_TAG_RES_END-->","");

                    SingerSingleMusic singerSingleMusic = gson.fromJson(data, SingerSingleMusic.class);

                    SingerSingleMusicAdapter singerSingleMusicAdapter=new SingerSingleMusicAdapter(singerSingleMusic);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(SingerActivity.this);

                    singer_single_music_list.setLayoutManager(layoutManager);

                    singer_single_music_list.setAdapter(singerSingleMusicAdapter);

                    singerSingleMusicAdapter.setListener(pos -> {

                        String hash = singerSingleMusic.getData().getInfo().get(pos).getHash();
                        String albumId = singerSingleMusic.getData().getInfo().get(pos).getAlbumId();
                        URL url = null;
                        try {
                            url = new URL("https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=jQuery19108335867548501714_1603904292877&hash=" +hash + "&album_id=" +  albumId+ "&dfid=1tgQ7z1EVOHH196APV4Tfoja&mid=f2eed631d990ecd18a86cc6e39867770&platid=4&_=1603943086161");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        String mvhash = singerSingleMusic.getData().getInfo().get(pos).getMvhash();
                        musicInfo.setMvHash(mvhash);
                        HttpUtil.sendGetRequest(url, handler, SEARCH_MUSIC_PLAY_URL);

                    });
                    break;
                case SEARCH_MUSIC_PLAY_URL:
                    data = data.replace("jQuery19108335867548501714_1603904292877(", "");
                    int s = data.lastIndexOf(";");
                    data = data.substring(0, s);
                    int j = data.lastIndexOf(")");
                    data = data.substring(0, j);
                    SearchMusicPlayUrlData currentMusic = gson.fromJson(data, SearchMusicPlayUrlData.class);
                    musicInfo.setMusicPlayUrlData(currentMusic);
                    playMusic(musicInfo);
                    break;
            }
        }
    };

    /**
     * 初始化歌手主页视图
     */
    private void initSingerInfoView(){
        singer_info_view=View.inflate(this,R.layout.singer_info_view,null);
        info=singer_info_view.findViewById(R.id.info);
    }

    /**
     * 初始化歌手单曲视图
     */
    private void initSingerSingleMusicView(){
        singer_single_music_view=View.inflate(this,R.layout.singer_single_music_view,null);

        singer_single_music_list=singer_single_music_view.findViewById(R.id.singer_single_music_list);
    }
}