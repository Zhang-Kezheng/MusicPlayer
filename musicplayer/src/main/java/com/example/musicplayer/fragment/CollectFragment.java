package com.example.musicplayer.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.HomePageActivity;
import com.example.musicplayer.adapter.CollectSingleMusicAdapter;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectFragment extends Fragment implements Observer , View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HomePageActivity activity;
    private CollectSingleMusicAdapter collectSingleMusicAdapter;
    private MusicPlayerApplication application;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CollectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CollectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectFragment newInstance(String param1, String param2) {
        CollectFragment fragment = new CollectFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        ImageView fragment_recent_back =view.findViewById(R.id.fragment_recent_back);
        fragment_recent_back.setOnClickListener(this);
        activity = (HomePageActivity) getActivity();
        assert activity != null;
        application = (MusicPlayerApplication) Objects.requireNonNull(getActivity()).getApplication();
        ViewPager collect_list_pager = view.findViewById(R.id.collect_list_pager);
        List<View> views = new ArrayList<>();
        View single_music_page = inflater.inflate(R.layout.single_music_page, null);
        View song_list_page = inflater.inflate(R.layout.song_list_page, null);
        View album_page = inflater.inflate(R.layout.album_page, null);
        View recent_video_page = inflater.inflate(R.layout.recent_video_page, null);
        init_single_music_page(single_music_page);
        views.add(single_music_page);
        views.add(song_list_page);
        views.add(album_page);
        views.add(recent_video_page);
        collect_list_pager.setAdapter(new PageAdapter(views));
        ImageView fragment_recent_page_indicator=view.findViewById(R.id.fragment_collect_page_indicator);
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
        return view;
    }
    /**
     * 初始化单曲列表
     *
     * @param view 单曲列表的页面对象
     */
    private void init_single_music_page(@NotNull View view) {
        ListView recent_single_music_list = view.findViewById(R.id.recent_single_music_list);
        collectSingleMusicAdapter = new CollectSingleMusicAdapter(getContext(), application);
        collectSingleMusicAdapter.setIndex(0);
        recent_single_music_list.setAdapter(collectSingleMusicAdapter);
        recent_single_music_list.setOnItemClickListener((parent, view1, position, id) -> {
            collectSingleMusicAdapter.setIndex(position);
            application.appSet.setCurrentSongList("我喜欢");
            application.appSet.setMusicInfos(application.appSet.getSongList().get("我喜欢"));
            activity.getConnection().getMusicControl().changeMusic(position);
            collectSingleMusicAdapter.notifyDataSetChanged();
        });
    }
    /**
     * 更新视图
     *
     * @param command 命令
     */
    @Override
    public void update(int command) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
