package com.example.musicplayer.activity;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.commons.MusicService;
import com.example.musicplayer.commons.MusicServiceConnect;
import com.example.musicplayer.util.AnimatorUtil;

import java.util.Objects;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;
import static com.example.musicplayer.commons.MusicPlayerApplication.UPDATE_UI;

/**
 * @author 章可政
 * @date 2021/1/12 23:06
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, Observer {
    /**
     * 音乐服务链接
     */
    protected MusicServiceConnect connection;
    /**
     * 应用
     */
    protected MusicPlayerApplication application;
    /**
     * 是否全屏
     */
    protected boolean isFullScreen = false;
    /**
     * 唱片图片
     */
    protected ImageView recode;
    /**
     * 播放按钮
     */
    protected ImageView play;

    /**
     * 下一首按钮
     */
    protected ImageView next;
    /**
     * 播放列表按钮
     */
    protected ImageView playlist;
    /**
     * 使图片旋转的实例
     */
    protected ObjectAnimator animator;
    /**
     * 当前音乐名
     */
    protected TextView currentPlayMusicName;
    /**
     * 播放列表视图
     */
    protected ListView musics;
    /**
     * 使图片变成圆形
     */
    protected final RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
    /**
     * 播放列表页面的适配器
     */
    protected MusicListAdapter adapter;
    /**
     * 弹窗
     */
    protected View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen(isFullScreen);
        setContentLayout();
        connectMusicService();
        beforeInitView();
        initCommonView();
        initView();
        afterInitView();
    }


    @Override
    protected void onDestroy() {
        connection.getMusicControl().getSubject().removeObserver(this);
        super.onDestroy();
    }

    /**
     * 是否全屏
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
    }

    public void connectMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        connection = new MusicServiceConnect();
        bindService(intent, connection, BIND_AUTO_CREATE);
        connection.setActivity(this);
        //绑定服务
        bindService(intent, connection, BIND_AUTO_CREATE);
        application = (MusicPlayerApplication) getApplication();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentLayout();

    /**
     * 在实例化控件之前的逻辑操作
     */
    public abstract void beforeInitView();

    /**
     * 实例化控件
     */
    public abstract void initView();

    /**
     * 实例化控件之后的操作
     */
    public abstract void afterInitView();

    /**
     * onClick方法的封装
     */
    public abstract void onClickEvent(View v);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.currentPlayMusicName://当前播放歌曲名
            case R.id.recode://唱片
                //跳转去歌词页面
                startActivity(PlayActivity.class);
                break;
            case R.id.play://播放按钮
                //播放
                connection.getMusicControl().play();
                break;
            case R.id.next://下一首
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                connection.getMusicControl().next();
                animator.start();
                break;
            case R.id.playlist://播放列表
                ViewGroup parent = (ViewGroup) dialogView.getParent();
                if (parent != null) {
                    parent.removeView(dialogView);
                }
                new MyDialog(dialogView, this, R.style.NormalDialogStyle);
                break;
        }
        onClickEvent(v);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获得屏幕的宽度
     */
    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 获得屏幕的高度
     */
    public int getScreenHeigh() {
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeigh = dm.heightPixels;
        return screenHeigh;
    }

    // ----------Activity跳转----------//
    protected void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
    }

    // 带参数跳转
    protected void startActivity(Class<?> targetClass, String key, String value) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    // 带请求码跳转
    protected void startActivity(Class<?> targetClass, int requestCode) {
        Intent intent = new Intent(this, targetClass);
        startActivityForResult(intent, requestCode);
    }

    // 带参数和请求码跳转
    protected void startActivity(Class<?> targetClass, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void initCommonView() {
        recode = findViewById(R.id.recode);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        playlist = findViewById(R.id.playlist);
        dialogView = View.inflate(this, R.layout.dialog_bottom, null);
        animator = AnimatorUtil.build(recode);
        currentPlayMusicName = findViewById(R.id.currentPlayMusicName);
        musics = dialogView.findViewById(R.id.musics);
        loadImage();
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        recode.setOnClickListener(this);
        playlist.setOnClickListener(this);
        currentPlayMusicName.setOnClickListener(this);
        adapter = new MusicListAdapter(this, application);
        musics.setAdapter(adapter);
        adapter.setIndex(application.appSet.getCurrentPlayPosition());
        musics.setOnItemClickListener((parent, view1, position, id) -> {
            connection.getMusicControl().changeMusic(position);
            animator.start();
        });
        if (application.appSet.getCurrentPlayPosition() != -1) {
            currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
        } else {
            currentPlayMusicName.setText("MusicPlayer");
        }
        if (application.isPlaying) {
            play.setImageResource(R.drawable.pause);
            animator.start();
        } else {
            play.setImageResource(R.drawable.play);
            animator.pause();
        }
    }

    @Override
    public void update(int command) {
        runOnUiThread(() -> {
            switch (command) {
                case PAUSE://暂停
                    //停止旋转动画
                    animator.pause();
                    //播放按钮设置成播放图片
                    play.setImageResource(R.drawable.play);
                    break;
                case PLAY://播放
                    //继续旋转动画
                    animator.resume();
                    //播放按钮设置成暂图片
                    play.setImageResource(R.drawable.pause);
                    break;
                case NEXT:
                    animator.start();
                    play.setImageResource(R.drawable.pause);
                    break;
                case UPDATE_UI:
                    currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
                    loadImage();
                    adapter.setIndex(application.appSet.getCurrentPlayPosition());
                    adapter.notifyDataSetChanged();
                    break;
            }
        });
    }

    /**
     * 通过网络资源加载圆形图片
     */
    protected void loadImage() {
        if (application.appSet.getCurrentPlayPosition() == -1) return;
        Glide.with(this)
                .load(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg())
                .apply(mRequestOptions)
                .into(recode);
    }
}
