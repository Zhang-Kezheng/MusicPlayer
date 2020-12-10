package com.example.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        ViewPager recent_list_pager = view.findViewById(R.id.recent_list_pager);
        List<View> views = new ArrayList<>();
        View single_music_page = inflater.inflate(R.layout.single_music_page, null);
        View song_list_page = inflater.inflate(R.layout.song_list_page, null);
        View album_page = inflater.inflate(R.layout.album_page, null);
        View recent_video_page = inflater.inflate(R.layout.recent_video_page, null);
        views.add(single_music_page);
        views.add(song_list_page);
        views.add(album_page);
        views.add(recent_video_page);
        recent_list_pager.setAdapter(new PageAdapter(views));
        init_single_music_page(single_music_page);
        init_recent_video_page(recent_video_page);
        ImageView fragment_recent_page_indicator=view.findViewById(R.id.fragment_recent_page_indicator);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) fragment_recent_page_indicator.getLayoutParams();
        float horizontalBias = layoutParams.horizontalBias;
        recent_list_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        return view;
    }

    /**
     * 初始化单曲列表
     *
     * @param view 单曲列表的页面对象
     */
    private void init_single_music_page(@NotNull View view) {
        ListView recent_single_music_list = view.findViewById(R.id.recent_single_music_list);
        recentSingleMusicAdapter = new RecentSingleMusicAdapter(getContext(), application);
        recentSingleMusicAdapter.setIndex(0);
        recent_single_music_list.setAdapter(recentSingleMusicAdapter);
        recent_single_music_list.setOnItemClickListener((parent, view1, position, id) -> {
            recentSingleMusicAdapter.setIndex(position);
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
            recentSingleMusicAdapter.setIndex(0);
            recentSingleMusicAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        activity.onClick(v);
    }
}
