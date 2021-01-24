package com.example.musicplayer.activity;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.commons.MusicServiceConnect;
import com.example.musicplayer.util.AnimatorUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;

/**
 * @author 章可政
 * @date 2021/1/12 23:17
 */
public class MainActivity extends BaseActivity {
    /**
     * 动画图片偏移量
     */
    private int offset = 0;
    /**
     * 当前页卡编号
     */
    private int currIndex = 0;
    /**
     * 动画图片宽度
     */
    private int bmpW;
    /**
     * 主页的pager
     */
    private ViewPager main_pager;
    /**
     * 我的
     */
    private TextView my_title;
    /**
     * 发现
     */
    private TextView discover_title;
    /**
     * 视频
     */
    private TextView video_title;
    /**
     * 三个标题视图的集合
     */
    private List<TextView> textViews;
    /**
     * 最近播放的音乐数量
     */
    private TextView recent_music_count;
    /**
     * 我的收藏的音乐数量
     */
    private TextView my_collect_count;
    /**
     * 我的页面视图
     */
    private View my;

    /**
     * 发现的页面视图
     */
    private View discover;
    /**
     * 视频的页面视图
     */
    private View video;
    /**
     * 三个页面的集合
     */
    private List<View> pages;
    /**
     * 滑块
     */
    private ImageView my_img;
    /**
     * 最近播放
     */
    private LinearLayout recent;
    /**
     * 收藏
     */
    private LinearLayout collect;
    /**
     * 搜索按钮
     */
    private ImageView search;

    private TextView day;

    private TextView month;

    private TextView week;

    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void beforeInitView() {
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 实例化所有控件
     */
    @Override
    public void initView() {
        my_title = findViewById(R.id.my_title);
        discover_title = findViewById(R.id.discover_title);
        video_title = findViewById(R.id.video_title);
        main_pager = findViewById(R.id.main_pager);
        my = View.inflate(this, R.layout.my_page, null);
        discover = View.inflate(this, R.layout.discover_page, null);
        video = View.inflate(this, R.layout.video_page, null);
        my_img = findViewById(R.id.my_img);
        recent = my.findViewById(R.id.recent);
        collect = my.findViewById(R.id.collect);
        search = my.findViewById(R.id.search);
        day=my.findViewById(R.id.day);
        month=my.findViewById(R.id.month);
        week=my.findViewById(R.id.week);
        recent_music_count = my.findViewById(R.id.recent_music_count);
        my_collect_count = my.findViewById(R.id.my_collect_count);
        pages = new ArrayList<>();
        textViews = new ArrayList<>();
    }

    @Override
    public void afterInitView() {
        loadImage();//加载图片
        pages.add(my);
        pages.add(discover);
        pages.add(video);
        textViews.add(my_title);
        textViews.add(discover_title);
        textViews.add(video_title);
        recent.setOnClickListener(this);
        collect.setOnClickListener(this);
        search.setOnClickListener(this);
        day.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"");
        month.setText(monthFormat(Calendar.getInstance().get(Calendar.MONTH)));
        week.setText(weekFormat(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
        if (application.appSet.getRecentPlay() != null) {
            recent_music_count.setText(application.appSet.getRecentPlay().size() + "");
        }
        if (application.appSet.getCollect() != null) {
            my_collect_count.setText(application.appSet.getCollect().size() + "");
        }
        PageAdapter pageAdapter = new PageAdapter(pages);
        main_pager.setAdapter(pageAdapter);
        main_pager.setCurrentItem(0, true);
        textViews.get(0).setTextColor(Color.parseColor("#000000"));
        textViews.get(0).setTextSize(20);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a5u).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
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
    }

    @Override
    public void onClickEvent(View v) {
        switch (v.getId()) {
            case R.id.search:
                //TODO 跳转搜索页面
                startActivity(SearchActivity.class);
                break;
            case R.id.recent:
                //TODO 最近页面
                startActivity(RecentActivity.class);
                break;
            case R.id.collect:
                //TODO 收藏页面
                startActivity(CollectActivity.class);
                break;
            case R.id.my_title:
                main_pager.setCurrentItem(textViews.indexOf(my_title), true);
                break;
            case R.id.discover_title:
                main_pager.setCurrentItem(textViews.indexOf(discover_title), true);
                break;
            case R.id.video_title:
                main_pager.setCurrentItem(textViews.indexOf(video_title), true);
                break;
        }
    }

    @Override
    public void update(int command) {
        super.update(command);
        //在ui线程里更新视图
        runOnUiThread(() -> {
            if (command == UPDATE_UI) {
                if (application.appSet.getRecentPlay() != null) {
                    recent_music_count.setText(application.appSet.getRecentPlay().size() + "");
                }
                if (application.appSet.getCollect() != null) {
                    my_collect_count.setText(application.appSet.getCollect().size() + "");
                }
            }
        });
    }

    /**
     * 重写onBackPressed方法，使返回后不退出程序，而是后台运行
     */
    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    /**
     * 月份格式化
     * @param month 月份
     * @return 英文的月份缩写
     */
    private String monthFormat(int month){
        String monthStr=null;
        switch (month+1){
            case 1:
                monthStr="JAN";
                break;
            case 2:
                monthStr="FEB";
                break;
            case 3:
                monthStr="MAR";
                break;
            case 4:
                monthStr="APR";
                break;
            case 5:
                monthStr="MAY";
                break;
            case 6:
                monthStr="JUN";
                break;
            case 7:
                monthStr="JUL";
                break;
            case 8:
                monthStr="AUG";
                break;
            case 9:
                monthStr="SEP";
                break;
            case 10:
                monthStr="OCT";
                break;
            case 11:
                monthStr="NOV";
                break;
            case 12:
                monthStr="DEC";
                break;

        }
        return monthStr;
    }

    /**
     * 星期格式化
     * @param day_of_week 一天在一星期的第几天
     * @return 汉字的周几
     */
    private String weekFormat(int day_of_week){
        String weekStr=null;
        switch (day_of_week-1){
            case 1:
                weekStr="周一";
                break;
            case 2:
                weekStr="周二";
                break;
            case 3:
                weekStr="周三";
                break;
            case 4:
                weekStr="周四";
                break;
            case 5:
                weekStr="周五";
                break;
            case 6:
                weekStr="周六";
                break;
            case 7:
                weekStr="周日";
                break;
        }
        return weekStr;
    }
}
