package com.example.musicplayer.activity;

import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
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

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener, Observer, Subject {
    private MusicPlayerApplication application;//当前应用
    private ImageView recode;
    private ImageView play;
    public ObjectAnimator animator;
    private MusicServiceConnect connection;
    private TextView currentPlayMusicName;
    private final RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
    private MusicListAdapter adapter;
    private View dialogView;//dialog的页面视图
    private final Vector<Observer> observers = new Vector<>();
    public MusicServiceConnect getConnection() {
        return connection;
    }
    @Override
    public void update(int command) {
        runOnUiThread(() -> {
            notifyObservers(command);
            switch (command) {
                case PAUSE:
                    animator.pause();
                    play.setImageResource(R.drawable.play);
                    break;
                case PLAY:
                    animator.resume();
                    play.setImageResource(R.drawable.pause);
                    break;
                case NEXT:
                    animator.start();
                    play.setImageResource(R.drawable.pause);
                    break;
                case UPDATE_UI:
                    currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
                    loadImage();
//                    RecentFragment recentFragment = (RecentFragment) getSupportFragmentManager().findFragmentByTag("recentFragment");
//                    if (recentFragment!=null){
//                        recentFragment.update(UPDATE_UI);
//                    }
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = new Intent(this, MusicService.class);
        connection = new MusicServiceConnect();
        bindService(intent, connection, BIND_AUTO_CREATE);
        connection.setActivity(this);
        application = (MusicPlayerApplication) getApplication();
        bindService(intent, connection, BIND_AUTO_CREATE);
        recode=findViewById(R.id.recode);
        play=findViewById(R.id.play);
        ImageView next = findViewById(R.id.next);
        ImageView playlist = findViewById(R.id.playlist);
        dialogView = View.inflate(this, R.layout.dialog_bottom, null);
        animator = AnimatorUtil.build(recode);
        currentPlayMusicName = findViewById(R.id.currentPlayMusicName);
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
        loadImage();
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
        Intent intent;
        switch (v.getId()) {
            case R.id.recode:
                intent = new Intent(this, PlayActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.play:
                connection.getMusicControl().play();
                break;
            case R.id.next:
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                connection.getMusicControl().next();
                animator.start();
                break;
            case R.id.playlist:
                //TODO 实现播放列表
                ViewGroup parent = (ViewGroup) dialogView.getParent();
                if (parent != null) {
                    parent.removeView(dialogView);
                }
                new MyDialog(dialogView, this);
                break;
            case R.id.fragment_recent_back:
                onBackPressed();
                break;
        }
    }

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

    @Override
    public void registerObserver(com.example.musicplayer.Observer o) {
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
