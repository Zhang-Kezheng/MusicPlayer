package com.example.musicplayer.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.CollectSingleMusicAdapter;
import com.example.musicplayer.adapter.PageAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CollectActivity extends BaseActivity {
    private CollectSingleMusicAdapter collectSingleMusicAdapter;
    private View single_music_page;
    private View song_list_page;
    private View album_page;
    private View recent_video_page;
    private ViewPager collect_list_pager;
    private List<View> views;
    private ImageView fragment_recent_page_indicator;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_collect);
    }

    @Override
    public void beforeInitView() {

    }

    @Override
    public void initView() {
        ImageView fragment_recent_back = findViewById(R.id.fragment_recent_back);
        fragment_recent_back.setOnClickListener(this);
        collect_list_pager = findViewById(R.id.collect_list_pager);
        views = new ArrayList<>();
        single_music_page = View.inflate(this, R.layout.single_music_page, null);
        song_list_page = View.inflate(this, R.layout.song_list_page, null);
        album_page = View.inflate(this, R.layout.album_page, null);
        recent_video_page = View.inflate(this, R.layout.recent_video_page, null);
        fragment_recent_page_indicator = findViewById(R.id.fragment_collect_page_indicator);
    }

    @Override
    public void afterInitView() {
        init_single_music_page(single_music_page);
        views.add(single_music_page);
        views.add(song_list_page);
        views.add(album_page);
        views.add(recent_video_page);
        collect_list_pager.setAdapter(new PageAdapter(views));
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fragment_recent_page_indicator.getLayoutParams();
        float horizontalBias = layoutParams.horizontalBias;
        collect_list_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels == 0) return;
                layoutParams.horizontalBias = horizontalBias + positionOffsetPixels / 3240f;
                fragment_recent_page_indicator.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.fragment_recent_back:
                onBackPressed();
        }
    }

    /**
     * 初始化单曲列表
     *
     * @param view 单曲列表的页面对象
     */
    private void init_single_music_page(@NotNull View view) {
        ListView recent_single_music_list = view.findViewById(R.id.recent_single_music_list);
        collectSingleMusicAdapter = new CollectSingleMusicAdapter(this, application);
        collectSingleMusicAdapter.setIndex(0);
        recent_single_music_list.setAdapter(collectSingleMusicAdapter);
        recent_single_music_list.setOnItemClickListener((parent, view1, position, id) -> {
            collectSingleMusicAdapter.setIndex(position);
            if (!application.appSet.getCurrentSongList().equals("我喜欢")) {
                application.appSet.setCurrentSongList("我喜欢");
                application.musicInfos = application.appSet.getSongList().get("我喜欢");
            }
            connection.getMusicControl().changeMusic(position);
            collectSingleMusicAdapter.notifyDataSetChanged();
        });
    }
}