package com.example.musicplayer.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.HomePageActivity;
import com.example.musicplayer.activity.MVActivity;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.adapter.RecentSingleMusicAdapter;
import com.example.musicplayer.adapter.RecentVideoListAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.model.mv.MV;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 最近播放的fragment
 */
public class RecentFragment extends Fragment implements View.OnClickListener, Observer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /**
     * 当前fragment所依赖的activity
     */
    private HomePageActivity activity;
    private RecentSingleMusicAdapter recentSingleMusicAdapter;
    private MusicPlayerApplication application;
    private List<TextView> titles;
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private ViewPager recent_list_pager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentFragment newInstance(String param1, String param2) {
        RecentFragment fragment = new RecentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        ImageView fragment_recent_back =view.findViewById(R.id.fragment_recent_back);
        fragment_recent_back.setOnClickListener(this);
        activity = (HomePageActivity) getActivity();
        assert activity != null;
        application = (MusicPlayerApplication) Objects.requireNonNull(getActivity()).getApplication();
        recent_list_pager = view.findViewById(R.id.recent_list_pager);
        List<View> views = new ArrayList<>();
        View single_music_page = inflater.inflate(R.layout.single_music_page, null);
        View song_list_page = inflater.inflate(R.layout.song_list_page, null);
        View album_page = inflater.inflate(R.layout.album_page, null);
        View recent_video_page = inflater.inflate(R.layout.recent_video_page, null);
        views.add(single_music_page);
        views.add(song_list_page);
        views.add(album_page);
        views.add(recent_video_page);
        titles=new ArrayList<>();
        TextView recent_title_single_music=view.findViewById(R.id.recent_title_single_music);
        TextView recent_title_music_list=view.findViewById(R.id.recent_title_music_list);
        TextView recent_title_album=view.findViewById(R.id.recent_title_album);
        TextView recent_title_video=view.findViewById(R.id.recent_title_video);
        recent_title_single_music.setOnClickListener(this);
        recent_title_music_list.setOnClickListener(this);
        recent_title_album.setOnClickListener(this);
        recent_title_video.setOnClickListener(this);
        titles.add(recent_title_single_music);
        titles.add(recent_title_music_list);
        titles.add(recent_title_album);
        titles.add(recent_title_video);
        recent_list_pager.setAdapter(new PageAdapter(views));
        init_single_music_page(single_music_page);
        init_recent_video_page(recent_video_page);
        recent_title_single_music.setText("单曲/"+application.appSet.getRecentPlay().size());
        ImageView fragment_recent_page_indicator=view.findViewById(R.id.fragment_recent_page_indicator);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a5u).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
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
                    if (position==i){
                        switch (position){
                            case 0:
                                titles.get(position).setText("单曲/"+application.appSet.getRecentPlay().size());
                                break;
                            case 1:
                                titles.get(position).setText("歌单/"+application.appSet.getRecentPlay().size());
                                break;
                            case 2:
                                titles.get(position).setText("专辑/"+application.appSet.getRecentPlay().size());
                                break;
                            case 3:
                                titles.get(position).setText("视频/"+application.appSet.getRecentPlay().size());
                                break;
                        }
                    }else {
                        switch (i){
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
        return view;
    }

    /**
     * 初始化单曲列表
     *
     * @param view 单曲列表的页面对象
     */
    private void init_single_music_page(@NotNull View view) {
        ListView recent_single_music_list = view.findViewById(R.id.recent_single_music_list);
        recentSingleMusicAdapter = new RecentSingleMusicAdapter(getContext(), application.appSet.getRecentPlay());
        recentSingleMusicAdapter.setIndex(0);
        recent_single_music_list.setAdapter(recentSingleMusicAdapter);
        recent_single_music_list.setOnItemClickListener((parent, view1, position, id) -> {
            recentSingleMusicAdapter.setIndex(position);
            if (!application.appSet.getCurrentSongList().equals("最近播放")){
                application.appSet.setCurrentSongList("最近播放");
                application.musicInfos=application.appSet.getSongList().get("最近播放");
            }
            activity.getConnection().getMusicControl().changeMusic(position);
            recentSingleMusicAdapter.notifyDataSetChanged();
        });
    }

    private void init_recent_video_page(View view){
        ListView recent_video_list = view.findViewById(R.id.recent_video_list);
        List<MV> mvList = application.appSet.getMvList();
        RecentVideoListAdapter recentVideoListAdapter = new RecentVideoListAdapter(mvList, this.getContext());
        recent_video_list.setAdapter(recentVideoListAdapter);
        recent_video_list.setOnItemClickListener((parent, view1, position, id) -> {
            MV mv = mvList.get(position);
            Intent intent=new Intent(activity, MVActivity.class);
            intent.putExtra("mv",mv);
            startActivity(intent);
        });
    }

    public void update(int command) {
        if (command==MusicPlayerApplication.UPDATE_UI){
            if (recentSingleMusicAdapter == null) {
                return;
            }
            recentSingleMusicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recent_title_single_music:
                recent_list_pager.setCurrentItem(0,true);
                break;
            case R.id.recent_title_music_list:
                recent_list_pager.setCurrentItem(1,true);
                break;
            case R.id.recent_title_album:
                recent_list_pager.setCurrentItem(2,true);
                break;
            case R.id.recent_title_video:
                recent_list_pager.setCurrentItem(3,true);
                break;
        }
        activity.onClick(v);
    }
}
