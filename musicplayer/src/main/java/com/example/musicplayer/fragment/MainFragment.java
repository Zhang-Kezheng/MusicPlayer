package com.example.musicplayer.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.activity.HomePageActivity;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainFragment extends Fragment implements View.OnClickListener , Observer {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private String text;
    private ViewPager main_pager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView my_title ;
    private TextView discover_title ;
    private TextView video_title ;
    private List<TextView> textViews;
    private TextView recent_music_count;
    private TextView my_collect_count;
    private MusicPlayerApplication application;
    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

        View view= inflater.inflate(R.layout.fragment_main, container, false);
        main_pager =view.findViewById(R.id.main_pager);
        View my = inflater.inflate(R.layout.my_page, null);
        View discover = inflater.inflate(R.layout.discover_page, null);
        View video = inflater.inflate(R.layout.video_page, null);
        List<View> pages = new ArrayList<>();
        textViews = new ArrayList<>();
        pages.add(my);
        pages.add(discover);
        pages.add(video);
        my_title=view.findViewById(R.id.my_title);
        discover_title=view.findViewById(R.id.discover_title);
        video_title=view.findViewById(R.id.video_title);
        textViews.add(my_title);
        textViews.add(discover_title);
        textViews.add(video_title);
        my_title.setOnClickListener(this);
        discover_title.setOnClickListener(this);
        video_title.setOnClickListener(this);
        ImageView my_img = view.findViewById(R.id.my_img);
        LinearLayout recent = my.findViewById(R.id.recent);
        ImageView search = my.findViewById(R.id.search);
        recent_music_count=my.findViewById(R.id.recent_music_count);
        my_collect_count=my.findViewById(R.id.my_collect_count);
        application= (MusicPlayerApplication) Objects.requireNonNull(getActivity()).getApplication();
        if (application.appSet.getRecentPlay()!=null){
            recent_music_count.setText(application.appSet.getRecentPlay().size()+"");
        }
        if (application.appSet.getCollect()!=null){
            my_collect_count.setText(application.appSet.getCollect().size()+"");
        }
        recent.setOnClickListener(this);
        search.setOnClickListener(this);
        PageAdapter pageAdapter = new PageAdapter(pages);
        main_pager.setAdapter(pageAdapter);
        main_pager.setCurrentItem(0,true);
        textViews.get(0).setTextColor(Color.parseColor("#000000"));
        textViews.get(0).setTextSize(20);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a5u).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 3 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        my_img.setImageMatrix(matrix);
        main_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            final int one = offset * 2 + bmpW;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float offX = positionOffsetPixels / 180f;
                if (position != 2) {
                    textViews.get(position + 1).setTextColor(Color.parseColor("#000000"));
                    textViews.get(position + 1).setTextSize(14 + offX);
                }
                textViews.get(position).setTextColor(Color.parseColor("#000000"));
                textViews.get(position).setTextSize(20 - offX);
            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = new TranslateAnimation(one * currIndex, one * position, 0, 0);//显然这个比较简洁，只有一行代码。
                currIndex = position;
                animation.setFillAfter(true);
                animation.setDuration(300);
                my_img.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        HomePageActivity activity = (HomePageActivity) getActivity();
        assert activity != null;
        switch (v.getId()){
            case R.id.search:
                activity.addFragment(new SearchFragment(),"searchFragment");
                break;
            case R.id.recent:
                activity.addFragment(new RecentFragment(),"recentFragment");
                break;
            case R.id.my_title:
                main_pager.setCurrentItem(textViews.indexOf(my_title),true);
                break;
            case R.id.discover_title:
                main_pager.setCurrentItem(textViews.indexOf(discover_title),true);
                break;
            case R.id.video_title:
                main_pager.setCurrentItem(textViews.indexOf(video_title),true);
                break;
        }
    }

    @Override
    public void update(int command) {
        switch (command){
            case MusicPlayerApplication.UPDATE_UI:
                if (application.appSet.getRecentPlay()!=null){
                    recent_music_count.setText(application.appSet.getRecentPlay().size()+"");
                }
                if (application.appSet.getCollect()!=null){
                    my_collect_count.setText(application.appSet.getCollect().size()+"");
                }
                break;
        }
    }
}
