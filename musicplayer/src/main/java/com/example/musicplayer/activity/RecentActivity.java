package com.example.musicplayer.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.adapter.RecentSingleMusicAdapter;
import com.example.musicplayer.adapter.RecentVideoListAdapter;
import com.example.musicplayer.model.mv.MV;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecentActivity extends BaseActivity {
    private RecentSingleMusicAdapter recentSingleMusicAdapter;
    private List<TextView> titles;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private ViewPager recent_list_pager;
    private ImageView fragment_recent_back;//返回按钮
    private TextView recent_title_single_music;
    private TextView recent_title_music_list;
    private TextView recent_title_album;
    private TextView recent_title_video;
    private List<View> views;//页面集合
    private View single_music_page;//单曲页
    private View song_list_page;//歌单页
    private View album_page;//专辑页
    private View recent_video_page;//视频页
    private ImageView fragment_recent_page_indicator;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_recent);
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        fragment_recent_back = findViewById(R.id.fragment_recent_back);
        recent_list_pager = findViewById(R.id.recent_list_pager);
        single_music_page = View.inflate(this, R.layout.single_music_page, null);
        song_list_page = View.inflate(this, R.layout.song_list_page, null);
        album_page = View.inflate(this, R.layout.album_page, null);
        recent_video_page = View.inflate(this, R.layout.recent_video_page, null);
        titles = new ArrayList<>();
        views = new ArrayList<>();
        recent_title_single_music = findViewById(R.id.recent_title_single_music);
        recent_title_music_list = findViewById(R.id.recent_title_music_list);
        recent_title_album = findViewById(R.id.recent_title_album);
        recent_title_video = findViewById(R.id.recent_title_video);
    }

    @Override
    public void afterInitView() {
        views.add(single_music_page);
        views.add(song_list_page);
        views.add(album_page);
        views.add(recent_video_page);
        titles.add(recent_title_single_music);
        titles.add(recent_title_music_list);
        titles.add(recent_title_album);
        titles.add(recent_title_video);
        fragment_recent_back.setOnClickListener(this);
        recent_title_single_music.setOnClickListener(this);
        recent_title_music_list.setOnClickListener(this);
        recent_title_album.setOnClickListener(this);
        recent_title_video.setOnClickListener(this);
        recent_list_pager.setAdapter(new PageAdapter(views));
        init_single_music_page(single_music_page);
        init_recent_video_page(recent_video_page);
        recent_title_single_music.setText("单曲/" + application.appSet.getRecentPlay().size());
        fragment_recent_page_indicator = findViewById(R.id.fragment_recent_page_indicator);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a5u).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float screenW = dm.widthPixels;
        offset = (int) ((screenW / 4 - bmpW) / 2);
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        fragment_recent_page_indicator.setImageMatrix(matrix);
        recent_list_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            final float one = offset * 2 + bmpW;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float offX = positionOffsetPixels / 180f;
                if (position != 3) {
                    titles.get(position + 1).setTextColor(Color.parseColor("#000000"));
                    titles.get(position + 1).setTextSize(14 + offX);
                }
                titles.get(position).setTextColor(Color.parseColor("#000000"));
                titles.get(position).setTextSize(20 - offX);
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < titles.size(); i++) {
                    if (position == i) {
                        switch (position) {
                            case 0:
                                titles.get(position).setText("单曲/" + application.appSet.getRecentPlay().size());
                                break;
                            case 1:
                                titles.get(position).setText("歌单/" + application.appSet.getRecentPlay().size());
                                break;
                            case 2:
                                titles.get(position).setText("专辑/" + application.appSet.getRecentPlay().size());
                                break;
                            case 3:
                                titles.get(position).setText("视频/" + application.appSet.getRecentPlay().size());
                                break;
                        }
                    } else {
                        switch (i) {
                            case 0:
                                titles.get(i).setText("单曲");
                                break;
                            case 1:
                                titles.get(i).setText("歌单");
                                break;
                            case 2:
                                titles.get(i).setText("专辑");
                                break;
                            case 3:
                                titles.get(i).setText("视频");
                                break;
                        }
                    }
                }
                Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);//显然这个比较简洁，只有一行代码。
                currIndex = position;
                animation.setFillAfter(true);
                animation.setDuration(300);
                fragment_recent_page_indicator.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.recent_title_single_music:
                recent_list_pager.setCurrentItem(0, true);
                break;
            case R.id.recent_title_music_list:
                recent_list_pager.setCurrentItem(1, true);
                break;
            case R.id.recent_title_album:
                recent_list_pager.setCurrentItem(2, true);
                break;
            case R.id.recent_title_video:
                recent_list_pager.setCurrentItem(3, true);
                break;
            case R.id.fragment_recent_back:
                onBackPressed();
                break;
        }
    }

    /**
     * 初始化单曲列表
     *
     * @param view 单曲列表的页面对象
     */
    private void init_single_music_page(@NotNull View view) {
        ListView recent_single_music_list = view.findViewById(R.id.recent_single_music_list);
        recentSingleMusicAdapter = new RecentSingleMusicAdapter(this, application.appSet.getRecentPlay());
        recentSingleMusicAdapter.setIndex(0);
        recent_single_music_list.setAdapter(recentSingleMusicAdapter);
        recent_single_music_list.setOnItemClickListener((parent, view1, position, id) -> {
            recentSingleMusicAdapter.setIndex(position);
            if (!application.appSet.getCurrentSongList().equals("最近播放")) {
                application.appSet.setCurrentSongList("最近播放");
                application.musicInfos = application.appSet.getSongList().get("最近播放");
            }
            connection.getMusicControl().changeMusic(position);
            recentSingleMusicAdapter.notifyDataSetChanged();
        });
    }

    private void init_recent_video_page(View view) {
        ListView recent_video_list = view.findViewById(R.id.recent_video_list);
        List<MV> mvList = application.appSet.getMvList();
        RecentVideoListAdapter recentVideoListAdapter = new RecentVideoListAdapter(mvList, this);
        recent_video_list.setAdapter(recentVideoListAdapter);
        recent_video_list.setOnItemClickListener((parent, view1, position, id) -> {
            MV mv = mvList.get(position);
            Intent intent = new Intent(this, MVActivity.class);
            intent.putExtra("mv", mv);
            startActivity(intent);
        });
    }

}