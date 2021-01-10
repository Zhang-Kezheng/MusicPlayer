package com.example.musicplayer.activity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.view.*;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.Subject;
import com.example.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.commons.*;
import com.example.musicplayer.fragment.MainFragment;
import com.example.musicplayer.util.AnimatorUtil;


import java.util.Vector;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;

/**
 * 主页，欢迎页后的第一个页面，
 */
public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, Observer, Subject {
    /**
     * 当前应用
     */
    private MusicPlayerApplication application;
    /**
     * 唱片图片
     */
    private ImageView recode;
    /**
     * 播放按钮
     */
    private ImageView play;
    /**
     * 使图片旋转的实例
     */
    public ObjectAnimator animator;
    /**
     * 音乐服务连接
     */
    private MusicServiceConnect connection;
    /**
     * 当前音乐名
     */
    private TextView currentPlayMusicName;
    /**
     * 使图片变成圆形
     */
    private final RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
    /**
     * 播放列表页面的适配器
     */
    private MusicListAdapter adapter;
    /**
     * 弹窗
     */
    private View dialogView;//dialog的页面视图
    /**
     * 观察者模式的观察者列表
     */
    private final Vector<Observer> observers = new Vector<>();

    /**
     * 获取音乐服务连接
     * @return 音乐服务连接
     */
    public MusicServiceConnect getConnection() {
        return connection;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //连接musicService
        Intent intent = new Intent(this, MusicService.class);
        connection = new MusicServiceConnect();
        bindService(intent, connection, BIND_AUTO_CREATE);
        connection.setActivity(this);
        application = (MusicPlayerApplication) getApplication();
        //绑定服务
        bindService(intent, connection, BIND_AUTO_CREATE);
        recode=findViewById(R.id.recode);
        play=findViewById(R.id.play);
        ImageView next = findViewById(R.id.next);
        ImageView playlist = findViewById(R.id.playlist);
        dialogView = View.inflate(this, R.layout.dialog_bottom, null);
        animator = AnimatorUtil.build(recode);
        currentPlayMusicName = findViewById(R.id.currentPlayMusicName);
        currentPlayMusicName.setOnClickListener(this);
        if (application.appSet.getCurrentPlayPosition() != -1) {
            currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
        } else {
            currentPlayMusicName.setText("MusicPlayer");
        }
        ListView musics = dialogView.findViewById(R.id.musics);
        adapter = new MusicListAdapter(this, application);
        musics.setAdapter(adapter);
        adapter.setIndex(application.appSet.getCurrentPlayPosition());
        musics.setOnItemClickListener((parent, view1, position, id) -> {
            connection.getMusicControl().changeMusic(position);
            animator.start();
        });
        loadImage();//加载图片
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        recode.setOnClickListener(this);
        playlist.setOnClickListener(this);
        addFragment(new MainFragment(),"mainFragment");
    }

    /**
     * 添加fragment
     * @param fragment fragment对象
     * @param tag 标志
      */

    public void addFragment(Fragment fragment,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.home_pager, fragment,tag);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        registerObserver((Observer) fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        Intent intent;//创建一个意图
        switch (v.getId()) {
            case R.id.currentPlayMusicName://当前播放歌曲名
            case R.id.recode://唱片
                //跳转去歌词页面
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
                new MyDialog(dialogView, this,R.style.NormalDialogStyle);
                break;
            case R.id.fragment_recent_back://返回
                onBackPressed();
                break;
        }
    }

    /**
     * 重写onBackPressed方法，使返回后不退出程序，而是后台运行
     */
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount()==1){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return;
        }
        super.onBackPressed();
    }
    /**
     * 更新页面视图
     * @param command 命令
     */
    @Override
    public void update(int command) {
        //在ui线程里更新视图
        runOnUiThread(() -> {
            //更新所有fragment里的视图
            notifyObservers(command);
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
    private void loadImage() {
        if (application.appSet.getCurrentPlayPosition() == -1) return;
        Glide.with(this)
                .load(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg())
                .apply(mRequestOptions)
                .into(recode);
    }
    @Override
    public void registerObserver(com.example.musicplayer.Observer o)  {
        observers.add(o);
    }

    @Override
    public void removeObserver(com.example.musicplayer.Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(int command) {
        for (Observer observer : observers) {
            observer.update(command);
        }
    }
}
