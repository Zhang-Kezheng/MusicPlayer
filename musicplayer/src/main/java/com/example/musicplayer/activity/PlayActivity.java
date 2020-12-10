package com.example.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.*;
import com.example.musicplayer.adapter.PageAdapter;
import com.example.musicplayer.commons.BlurTransformation;
import com.example.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.commons.MusicService;
import com.example.musicplayer.model.mv.MV;
import com.example.musicplayer.model.mv.detail.MVDetail;
import com.example.musicplayer.model.mv.playurl.MVModel;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.util.HttpUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.zlm.hp.lyrics.LyricsReader;
import com.zlm.hp.lyrics.widget.ManyLyricsView;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, Observer, View.OnTouchListener {
    public static ManyLyricsView krcView;//歌词视图
    public ImageView play;//播放/暂停按钮
    @SuppressLint("StaticFieldLeak")
    private static SeekBar seekBar;//歌曲进度
    @SuppressLint("StaticFieldLeak")
    private static TextView currentDuration;//当前播放进度
    @SuppressLint("StaticFieldLeak")
    private static TextView duration;//歌曲总时长
    private ImageView playMode;//播放模式按钮
    private ListView musics;//音乐列表
    private TextView currentPlayMusicName;//歌曲名称
    private static MusicPlayerApplication application;//当前应用
    private ImageView bg;//背景图片
    private  MusicService.MusicControl musicControl;
    private MusicListAdapter adapter;
    private View dialogView;//dialog的页面视图
    private MV mv=new MV();
    private ImageView like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.play);
        application = (MusicPlayerApplication) getApplication();
        Intent intent = new Intent(this, MusicService.class);
        //音乐服务连接
        MusicServiceConnection connection = new MusicServiceConnection();
        bindService(intent, connection, BIND_AUTO_CREATE);
        initView();
        if (application.isPlaying) {
            play.setImageResource(R.drawable.playpause);
        }
    }
    void initView() {
        adapter = new MusicListAdapter(this, application);
        adapter.setIndex(application.appSet.getCurrentPlayPosition());
        krcView = findViewById(R.id.lrc);
        krcView.setSize(application.appSet.getLrc_set_lrc_size(), application.appSet.getLrc_set_lrc_size());
        krcView.setOnLrcClickListener(progress -> musicControl.seekTo(progress));
        krcView.setOnClickListener(this);
        bg=findViewById(R.id.bg);
        if (application.appSet.getCurrentMusic()!=null){
            Glide.with(this).load(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg())
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(this,24,10))).into(bg);
        }
        int[] Paints = {Color.WHITE, Color.WHITE};
        int[] PaintHLs = {Color.RED, Color.RED};
        krcView.setPaintColor(Paints);
        krcView.setPaintHLColor(PaintHLs);
        currentPlayMusicName = findViewById(R.id.musicTitle);
        if (application.appSet.getCurrentPlayPosition() != -1) {
            update(CHANGE_LRC);
            currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
        }
        dialogView = View.inflate(this, R.layout.dialog_bottom, null);
        musics = dialogView.findViewById(R.id.musics);
        musics.setAdapter(adapter);
        loadMusic();
        play = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekbar);
        //下一首
        ImageView next = findViewById(R.id.next);
        //上一首
        ImageView pre = findViewById(R.id.pre);
        currentDuration = findViewById(R.id.currentDuration);
        duration = findViewById(R.id.duration);
        //播放列表按钮
        ImageView playlist = findViewById(R.id.playlist);
        ImageView playMv = findViewById(R.id.playmv);
        ImageView back = findViewById(R.id.back);
        ImageView set = findViewById(R.id.set);
        ImageView lrcset = findViewById(R.id.lrc_set);
        like=findViewById(R.id.like);
        if (application.appSet.getCurrentMusic().isLike()){
            like.setImageResource(R.drawable.like);
        }else {
            like.setImageResource(R.drawable.unlike);
        }
        playMode = findViewById(R.id.playMode);
        like.setOnClickListener(this);
        playMv.setOnClickListener(this);
        playlist.setOnClickListener(this);
        playlist.setOnTouchListener(this);
        playMode.setOnClickListener(this);
        playMode.setOnTouchListener(this);
        play.setOnClickListener(this);
        play.setOnTouchListener(this);
        pre.setOnClickListener(this);
        pre.setOnTouchListener(this);
        next.setOnClickListener(this);
        next.setOnTouchListener(this);
        back.setOnClickListener(this);
        back.setOnTouchListener(this);
        set.setOnClickListener(this);
        set.setOnTouchListener(this);
        lrcset.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicControl.seekTo(progress);
                    krcView.seekto(progress);
                }
                krcView.postInvalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                musicControl.seekTo(progress);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicControl.getSubject().removeObserver(this);
    }


    @Override
    public void update(int command) {
        runOnUiThread(() -> {
            switch (command) {
                case PAUSE:
                    play.setImageResource(R.drawable.playplay);
                    break;
                case PLAY:
                case PRE:
                case NEXT:
                    currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
                    play.setImageResource(R.drawable.playpause);
                    break;
                case UPDATE_UI:
                    currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
                    adapter.setIndex(application.appSet.getCurrentPlayPosition());
                    adapter.notifyDataSetChanged();
                    break;
                case CHANGE_LRC:
                    LyricsReader lyricsReader = new LyricsReader();
                    File file = new File(MusicPlayerApplication.LRC_PATH + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash() + ".krc");
                    try {
                        lyricsReader.loadLrc(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    krcView.setLyricsReader(lyricsReader);
                    Glide.with(PlayActivity.this).load(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg())
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(PlayActivity.this,24,10))).into(bg);
                    break;
            }
        });
    }

    private void changeLight(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness,// 改变亮度
                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            changeLight((ImageView) v, 170);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            changeLight((ImageView) v, 0);
        }
        return false;
    }

    class MusicServiceConnection implements ServiceConnection {


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicService.MusicControl) service;
            musicControl.getSubject().registerObserver(PlayActivity.this);
            musicControl.setActivity(PlayActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println(">>>>>>>>>>>>>>>>>断开连接");
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                musicControl.play();
                break;
            case R.id.pre:
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                musicControl.pre();
                break;
            case R.id.next:
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                musicControl.next();
                break;
            case R.id.playlist:
            case R.id.set:
                ViewGroup parent = (ViewGroup) dialogView.getParent();
                if (parent != null) {
                    parent.removeView(dialogView);
                }
                new MyDialog(dialogView, this);
                break;
            case R.id.back:
                super.onBackPressed();
                break;
            case R.id.playMode:
                if (application.appSet.getCurrentMode().equals("随机播放")) {
                    playMode.setImageResource(R.drawable.one);
                    application.appSet.setCurrentMode("单曲循环");
                    return;
                }
                if (application.appSet.getCurrentMode().equals("单曲循环")) {
                    playMode.setImageResource(R.drawable.list);
                    application.appSet.setCurrentMode("列表循环");
                    return;
                }
                if (application.appSet.getCurrentMode().equals("列表循环")) {
                    playMode.setImageResource(R.drawable.ran);
                    application.appSet.setCurrentMode("随机播放");
                }
                break;
            case R.id.lrc:
                krcView.setVisibility(View.GONE);
                break;
            case R.id.like:
                List<MusicInfo> collect = application.appSet.getCollect();
                if (collect==null)collect=new ArrayList<>();
                if (application.appSet.getCurrentMusic().isLike()){
                    application.appSet.getCurrentMusic().setLike(false);
                    like.setImageResource(R.drawable.unlike);
                    Toast.makeText(this,"已取消收藏",Toast.LENGTH_SHORT).show();
                    collect.remove(application.appSet.getCurrentMusic());
                }else {
                    application.appSet.getCurrentMusic().setLike(true);
                    like.setImageResource(R.drawable.like);
                    Toast.makeText(this,"已收藏",Toast.LENGTH_SHORT).show();
                    collect.add(application.appSet.getCurrentMusic());
                }
                application.appSet.setCollect(collect);
                MusicPlayerApplication.serialization(application.appSet,MusicPlayerApplication.CONFIG_PATH+"appSet.conf");
                break;
            case R.id.lrc_set:
                View view = View.inflate(this, R.layout.lrc_set_layout, null);
                SeekBar lrc_set_lrc_size=view.findViewById(R.id.lrc_set_lrc_size);
                lrc_set_lrc_size.setMax(100);
                lrc_set_lrc_size.setMin(30);
                lrc_set_lrc_size.setProgress(application.appSet.getLrc_set_lrc_size());
                lrc_set_lrc_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            application.appSet.setLrc_set_lrc_size(progress);
                            krcView.setSize(progress,progress,true);
                        }
                        MusicPlayerApplication.serialization(application.appSet,MusicPlayerApplication.CONFIG_PATH+"appSet.conf");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                new MyDialog(view, this);
                break;
            case R.id.playmv:
                if (application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHaveMv()==1){
                    String mvHash=application.appSet.getCurrentMusic().getMvHash();
                    String key= DigestUtils.md5Hex(mvHash+ "kugoumvcloud");
                    URL url= null;
                    try {
                        url = new URL("http://trackermv.kugou.com/interface/index/cmd=100&hash="+mvHash +
                                "&key="+key+"&pid=6&ext=mp4&ismp3=0");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.sendGetRequest(url,handler,MV_PLAY_URL);
                }else {
                    Toast.makeText(PlayActivity.this,"该歌曲暂时没有mv",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void loadMusic() {
        if (application.appSet.getCurrentPlayPosition() == -1) return;
        adapter.notifyDataSetChanged();
        musics.setOnItemClickListener((parent, view, position, id) -> {
            musicControl.changeMusic(position);
           krcView.postInvalidate();
        });
    }
    public  Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int duration1 = bundle.getInt("duration");
            int currentDuration1 = bundle.getInt("currentDuration");
            seekBar.setMax(duration1);
            seekBar.setProgress(currentDuration1);
            duration.setText(timeToString(duration1));
            currentDuration.setText(timeToString(currentDuration1));
            Gson gson=new Gson();
            switch (msg.what){
                case MV_PLAY_URL:
                    String data = bundle.getString("data");
                    MVModel mvModel = gson.fromJson(data, MVModel.class);
                    mv.setMvModel(mvModel);
                    URL url= null;
                    try {
                        url = new URL("http://mobilecdn.kugou.com/api/v3/mv/detail?area_code=1&plat=0&mvhash="+application.appSet.getCurrentMusic().getMvHash()+"&with_res_tag=1");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.sendGetRequest(url,handler,MV_DETAIL);
                    break;
                case MV_DETAIL:
                    data = bundle.getString("data");
                    data=data.replace("<!--KG_TAG_RES_START-->","");
                    data=data.replace("<!--KG_TAG_RES_END-->","");
                    MVDetail mvDetail = gson.fromJson(data, MVDetail.class);
                    mv.setMvDetail(mvDetail);
                    saveMV(mv);
                    Intent intent=new Intent(PlayActivity.this, MVActivity.class);
                    intent.putExtra("mv",mv);
                    PlayActivity.this.startActivity(intent);
                    break;
            }
        }

        private String timeToString(int time) {
            int min = time / 1000 / 60;
            int second = time / 1000 % 60;
            String strMin;
            String strSecond;
            if (min < 10) {
                strMin = "0" + min;
            } else {
                strMin = min + "";
            }
            if (second < 10) {
                strSecond = "0" + second;
            } else {
                strSecond = "" + second;
            }
            return strMin + ":" + strSecond;
        }
    };
    /**
     * 保存mv信息进本地
     * @param mv mv数据
     */
    private void saveMV(MV mv) {
        MusicPlayerApplication application= (MusicPlayerApplication) getApplication();
        if (application.appSet.getMvList()==null){
            application.appSet.setMvList(new ArrayList<>());
        }
        List<MV> mvList = application.appSet.getMvList();
        mvList.add(mv);
        application.appSet.setMvList(mvList);
        MusicPlayerApplication.serialization(application.appSet,MusicPlayerApplication.CONFIG_PATH+"appSet.conf");
    }
}