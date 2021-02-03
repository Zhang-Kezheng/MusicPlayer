package com.example.musicplayer.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.musicplayer.*;
import com.example.musicplayer.Observer;
import com.example.musicplayer.commons.AppSet;
import com.example.musicplayer.commons.BlurTransformation;
import com.example.musicplayer.adapter.MusicListAdapter;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.commons.MusicService;
import com.example.musicplayer.model.music.searchmusicplayurl.Author;
import com.example.musicplayer.model.mv.MV;
import com.example.musicplayer.model.mv.detail.MVDetail;
import com.example.musicplayer.model.mv.playurl.MVModel;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.set.ApplicationTypeFace;
import com.example.musicplayer.set.MusicPlayMode;
import com.example.musicplayer.util.HttpUtil;
import com.example.musicplayer.util.ImageUtils;
import com.google.gson.Gson;
import com.zlm.hp.lyrics.LyricsReader;
import com.zlm.hp.lyrics.widget.ManyLyricsView;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.example.musicplayer.commons.MusicPlayerApplication.*;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, Observer {
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
    private MusicService.MusicControl musicControl;
    private MusicListAdapter adapter;
    private final MV mv = new MV();
    private ImageView like;
    private MyDialog myDialog;
    private MyDialog playlistDialog;
    private AudioManager am;//audio管理器
    private int current;//当前音量
    private SeekBar play_volume;//音量
    private MyDialog lrcSetDialog;//歌词设置弹窗
    private View lrcSetView;
    private Typeface 站酷文艺体;
    private Typeface 优设标题黑;
    private Typeface 华康少女体;
    private List<LinearLayout> typefaceList;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.play);
        application = (MusicPlayerApplication) getApplication();
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        Intent intent = new Intent(this, MusicService.class);
        //音乐服务连接
        MusicServiceConnection connection = new MusicServiceConnection();
        bindService(intent, connection, BIND_AUTO_CREATE);
        initView();
        if (application.isPlaying) {
            play.setImageResource(R.drawable.playpause);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        adapter = new MusicListAdapter(this, application);
        adapter.setIndex(application.appSet.getCurrentPlayPosition());
        krcView = findViewById(R.id.lrc);
        krcView.setSpaceLineHeight(application.appSet.getLrc_set_lrc_size() * 1.5f);//设置行高
        krcView.setSize(application.appSet.getLrc_set_lrc_size(), application.appSet.getLrc_set_lrc_size());
        krcView.setOnLrcClickListener(progress -> musicControl.seekTo(progress));
        krcView.setOnClickListener(this);
        initLrcSetView();
        initPlaySetView();
        bg = findViewById(R.id.bg);
        int brightness = -80; //RGB偏移量，变暗为负数
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(matrix);
        bg.setColorFilter(cmcf); //imageView为显示图片的View。
        if (application.appSet.getCurrentMusic() != null) {
            Glide.with(this).load(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg())
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(this, 24, 10))).into(bg);
        }
        int[] Paints = {Color.WHITE, Color.WHITE};
        int[] PaintHLs = {application.appSet.getLrc_color(), application.appSet.getLrc_color()};
        krcView.setPaintColor(Paints);
        krcView.setPaintHLColor(PaintHLs);
        currentPlayMusicName = findViewById(R.id.musicTitle);
        if (application.appSet.getCurrentPlayPosition() != -1) {
            update(CHANGE_LRC);
            currentPlayMusicName.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "\n" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
        }
        //dialog的页面视图
        View dialogView = View.inflate(this, R.layout.dialog_bottom, null);
        playlistDialog = new MyDialog(dialogView, this, R.style.NormalDialogStyle);
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
        ImageView comment = findViewById(R.id.comment);
        like = findViewById(R.id.like);
        if (application.appSet.getCurrentMusic() != null && application.appSet.getCurrentMusic().isLike()) {
            like.setImageResource(R.drawable.like);
        } else {
            like.setImageResource(R.drawable.unlike);
        }
        comment.setOnClickListener(this);
        playMode = findViewById(R.id.playMode);
        like.setOnClickListener(this);
        playMv.setOnClickListener(this);
        playlist.setOnClickListener(this);
        playMode.setOnClickListener(this);
        play.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        set.setOnClickListener(this);
        lrcset.setOnClickListener(this);
        switch (application.appSet.getPlayMode()) {
            case SingleCycle:
                playMode.setImageResource(R.drawable.one);
                break;
            case ListLoop:
                playMode.setImageResource(R.drawable.list);
                break;
            case ShufflePlayback:
                playMode.setImageResource(R.drawable.ran);
                break;
        }
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
                            .apply(RequestOptions.bitmapTransform(new BlurTransformation(PlayActivity.this, 24, 10))).into(bg);
                    break;
            }
        });
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
            case R.id.play://开始播放
                //如果当前歌曲播放位置为-1.则表示当前没有歌曲，点击播放按钮无效，直接返回
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                //播放
                musicControl.play();
                break;
            case R.id.pre://上一首
                //如果当前歌曲播放位置为-1.则表示当前没有歌曲，点击上一首按钮无效，直接返回
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                //上一首
                musicControl.pre();
                break;
            case R.id.next://下一首
                //如果当前歌曲播放位置为-1.则表示当前没有歌曲，点击下一首按钮无效，直接返回
                if (application.appSet.getCurrentPlayPosition() == -1) return;
                //下一首
                musicControl.next();
                break;
            case R.id.playlist://播放列表
                //弹出播放列表弹窗
                playlistDialog.show();
                break;
            case R.id.set:
                myDialog.show();
                break;
            case R.id.play_set_close_button:
                myDialog.dismiss();
                break;
            case R.id.back:
                super.onBackPressed();
                break;
            case R.id.playMode:
                playMode();//播放模式
                break;
            case R.id.lrc:
                krcView.setVisibility(View.GONE);
                break;
            case R.id.comment:
                Toast.makeText(this, "该功能正在开发中", Toast.LENGTH_SHORT).show();
                break;
            case R.id.like:
                like();
                break;
            case R.id.lrc_set:
                lrcSetDialog.show();
                break;
            case R.id.playmv:
                playmv();
                break;
            case R.id.lrc_family_zero://默认字体
                setTypeface(Typeface.DEFAULT);
                application.appSet.setTypeFace(ApplicationTypeFace.DEFAULT);
                setTypefaceItemBackgroundColor(0);
                break;
            case R.id.lrc_family_one://站酷文艺体
                setTypeface(站酷文艺体);
                application.appSet.setTypeFace(ApplicationTypeFace.站酷文艺体);
                setTypefaceItemBackgroundColor(1);
                break;
            case R.id.lrc_family_two://优设标题黑
                setTypeface(优设标题黑);
                application.appSet.setTypeFace(ApplicationTypeFace.优设标题黑);
                setTypefaceItemBackgroundColor(2);
                break;
            case R.id.lrc_family_three://华康少女体
                setTypeface(华康少女体);
                application.appSet.setTypeFace(ApplicationTypeFace.华康少女体);
                setTypefaceItemBackgroundColor(3);
                break;
            case R.id.lrc_color_red://红色
                changeView(v, image);
                setPaintHLColor(Color.RED);
                application.appSet.setLrc_color(Color.RED);
                break;
            case R.id.lrc_color_blue://蓝色
                changeView(v, image);
                setPaintHLColor(Color.BLUE);
                application.appSet.setLrc_color(Color.BLUE);
                break;
            case R.id.lrc_color_green://绿色
                changeView(v, image);
                setPaintHLColor(Color.GREEN);
                application.appSet.setLrc_color(Color.GREEN);
                break;
            case R.id.lrc_color_yellow:
                changeView(v, image);
                setPaintHLColor(Color.YELLOW);
                application.appSet.setLrc_color(Color.YELLOW);
                break;
            case R.id.lrc_color_purple:
                changeView(v, image);
                setPaintHLColor(Color.parseColor("#FF00FF"));
                application.appSet.setLrc_color(Color.parseColor("#FF00FF"));
                break;
            case R.id.chakangeshou:
                Intent intent=new Intent(this,SingerActivity.class);
                List<Author> authors = application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthors();
                if (authors.size()==1){
                    intent.putExtra("singerID", authors.get(0).getAuthorId());
                    startActivity(intent);
                    myDialog.dismiss();
                }else {
                    Toast.makeText(this,"两个歌手",Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                }
                break;
        }
        MusicPlayerApplication.serialization(application.appSet);
    }
    /**
     * 切换选中的高亮歌词颜色的图片
     *
     * @param view      被点击的视图
     * @param imageView 对勾图片视图
     */
    private void changeView(View view, ImageView imageView) {
        FrameLayout frameLayout = (FrameLayout) imageView.getParent();
        frameLayout.removeView(imageView);
        FrameLayout parent = (FrameLayout) view.getParent();
        parent.addView(imageView);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getAction()) {
            case KeyEvent.ACTION_UP://键盘松开ACTION_UP
            case KeyEvent.ACTION_DOWN: //键盘按下KeyEvent.ACTION_DOWN
                if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (play_volume != null) {
                        play_volume.setProgress(current);
                    }
                }

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 加载音乐
     */
    private void loadMusic() {
        if (application.appSet.getCurrentPlayPosition() == -1) return;
        adapter.notifyDataSetChanged();//更新播放列表数据
        musics.setOnItemClickListener((parent, view, position, id) -> {
            musicControl.changeMusic(position);//播放音乐
            krcView.postInvalidate();//更新歌词页面
        });
    }

    /**
     * 消息处理
     */
    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int duration1 = bundle.getInt("duration");
            int currentDuration1 = bundle.getInt("currentDuration");
            seekBar.setMax(duration1);
            seekBar.setProgress(currentDuration1);
            duration.setText(timeToString(duration1));
            currentDuration.setText(timeToString(currentDuration1));
            Gson gson = new Gson();
            switch (msg.what) {
                case MV_PLAY_URL:
                    String data = bundle.getString("data");
                    MVModel mvModel = gson.fromJson(data, MVModel.class);
                    mv.setMvModel(mvModel);
                    URL url = null;
                    try {
                        url = new URL("http://mobilecdn.kugou.com/api/v3/mv/detail?area_code=1&plat=0&mvhash=" + application.appSet.getCurrentMusic().getMvHash() + "&with_res_tag=1");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    HttpUtil.sendGetRequest(url, handler, MV_DETAIL);
                    break;
                case MV_DETAIL:
                    data = bundle.getString("data");
                    data = data.replace("<!--KG_TAG_RES_START-->", "");
                    data = data.replace("<!--KG_TAG_RES_END-->", "");
                    MVDetail mvDetail = gson.fromJson(data, MVDetail.class);
                    mv.setMvDetail(mvDetail);
                    saveMV(mv, application);
                    Intent intent = new Intent(PlayActivity.this, MVActivity.class);
                    intent.putExtra("mv", mv);
                    PlayActivity.this.startActivity(intent);
                    break;
            }
        }

        /**
         * 时间转换成字符去，用于显示歌曲时长
         * @param time 时长。单位毫秒
         * @return 毫秒数对应的分钟形式
         */
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
     *
     * @param mv mv数据
     */
    public static void saveMV(MV mv, MusicPlayerApplication application) {
        if (application.appSet.getMvList() == null) {
            application.appSet.setMvList(new ArrayList<>());
        }
        List<MV> mvList = application.appSet.getMvList();
        mvList.add(mv);
        application.appSet.setMvList(mvList);
    }

    /**
     * 初始化字体
     */
    private void initTypeface() {
        站酷文艺体 = Typeface.createFromAsset(getAssets(), "font/站酷文艺体.ttf");
        优设标题黑 = Typeface.createFromAsset(getAssets(), "font/优设标题黑.ttf");
        华康少女体 = Typeface.createFromAsset(getAssets(), "font/华康少女文字简W5.ttf");
    }

    /**
     * 设置字体
     *
     * @param typeface 字体
     */
    private void setTypeface(Typeface typeface) {
        krcView.setTypeFace(typeface, true);
    }

    /**
     * 设置歌词字体选项背景颜色
     *
     * @param index 下标
     */
    private void setTypefaceItemBackgroundColor(int index) {
        for (int i = 0; i < typefaceList.size(); i++) {
            if (i == index) {
                typefaceList.get(i).setBackgroundResource(R.drawable.typeface_item_background);
            } else {
                typefaceList.get(i).setBackgroundResource(R.color.qmui_config_color_10_white);
            }
        }
    }

    /**
     * 初始化歌词设置视图
     */
    private void initLrcSetView() {
        lrcSetView = View.inflate(this, R.layout.lrc_set_layout, null);
        lrcSetDialog = new MyDialog(lrcSetView, this, R.style.NormalDialogStyle);
        initTypeface();//初始化字体
        initLrcSizeView();
        initLrcTypeface();
        initLrcColor();
    }

    /**
     * 初始化歌词大小视图
     */
    private void initLrcSizeView() {
        SeekBar lrc_set_lrc_size = lrcSetView.findViewById(R.id.lrc_set_lrc_size);
        lrc_set_lrc_size.setMax(100);
        lrc_set_lrc_size.setMin(30);
        lrc_set_lrc_size.setProgress(application.appSet.getLrc_set_lrc_size());
        lrc_set_lrc_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    application.appSet.setLrc_set_lrc_size(progress);
                    krcView.setSize(progress, progress, true);
                    krcView.setSpaceLineHeight(progress * 1.5f, true);
                    MusicPlayerApplication.serialization(application.appSet);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 初始化歌词字体视图
     */
    private void initLrcTypeface() {
        TextView lrc_family_one_text = lrcSetView.findViewById(R.id.lrc_family_one_text);
        lrc_family_one_text.setTypeface(站酷文艺体);
        TextView lrc_family_two_text = lrcSetView.findViewById(R.id.lrc_family_two_text);
        lrc_family_two_text.setTypeface(优设标题黑);
        TextView lrc_family_three_text = lrcSetView.findViewById(R.id.lrc_family_two_text);
        lrc_family_three_text.setTypeface(优设标题黑);
        CardView lrc_family_zero = lrcSetView.findViewById(R.id.lrc_family_zero);
        CardView lrc_family_one = lrcSetView.findViewById(R.id.lrc_family_one);
        CardView lrc_family_two = lrcSetView.findViewById(R.id.lrc_family_two);
        CardView lrc_family_three = lrcSetView.findViewById(R.id.lrc_family_three);
        LinearLayout typeface_item_back_zero = lrcSetView.findViewById(R.id.typeface_item_back_zero);
        LinearLayout typeface_item_back_one = lrcSetView.findViewById(R.id.typeface_item_back_one);
        LinearLayout typeface_item_back_two = lrcSetView.findViewById(R.id.typeface_item_back_two);
        LinearLayout typeface_item_back_three = lrcSetView.findViewById(R.id.typeface_item_back_three);
        LinearLayout typeface_item_back_four = lrcSetView.findViewById(R.id.typeface_item_back_four);
        typefaceList = new ArrayList<>();
        typefaceList.add(typeface_item_back_zero);
        typefaceList.add(typeface_item_back_one);
        typefaceList.add(typeface_item_back_two);
        typefaceList.add(typeface_item_back_three);
        typefaceList.add(typeface_item_back_four);
        lrc_family_zero.setOnClickListener(this);
        lrc_family_one.setOnClickListener(this);
        lrc_family_two.setOnClickListener(this);
        lrc_family_three.setOnClickListener(this);
        switch (application.appSet.getTypeFace()) {
            case DEFAULT:
                setTypeface(Typeface.DEFAULT);
                setTypefaceItemBackgroundColor(0);
                break;
            case 站酷文艺体:
                setTypeface(站酷文艺体);
                setTypefaceItemBackgroundColor(1);
                break;
            case 优设标题黑:
                setTypeface(优设标题黑);
                setTypefaceItemBackgroundColor(2);
                break;
            case 华康少女体:
                setTypeface(华康少女体);
                setTypefaceItemBackgroundColor(3);
                break;
        }
    }

    /**
     * 初始化歌词颜色视图
     */
    private void initLrcColor() {
        image = new ImageView(this);
        image.setImageDrawable(getResources().getDrawable(R.drawable.duigou, getTheme())); //可用
        int lrc_color = application.appSet.getLrc_color();
        FrameLayout container = null;
        switch (lrc_color) {
            case Color.RED:
                container = lrcSetView.findViewById(R.id.lrc_color_red_container);
                break;
            case Color.BLUE:
                container = lrcSetView.findViewById(R.id.lrc_color_blue_container);
                break;
            case Color.GREEN:
                container = lrcSetView.findViewById(R.id.lrc_color_green_container);
                break;
            case Color.YELLOW:
                container = lrcSetView.findViewById(R.id.lrc_color_yellow_container);
                break;
            case -65281:
                container = lrcSetView.findViewById(R.id.lrc_color_purple_container);
                break;
        }
        if (container != null) container.addView(image);
        View lrc_color_red = lrcSetView.findViewById(R.id.lrc_color_red);
        lrc_color_red.setOnClickListener(this);
        View lrc_color_blue = lrcSetView.findViewById(R.id.lrc_color_blue);
        lrc_color_blue.setOnClickListener(this);
        View lrc_color_green = lrcSetView.findViewById(R.id.lrc_color_green);
        lrc_color_green.setOnClickListener(this);
        View lrc_color_yellow = lrcSetView.findViewById(R.id.lrc_color_yellow);
        lrc_color_yellow.setOnClickListener(this);
        View lrc_color_purple = lrcSetView.findViewById(R.id.lrc_color_purple);
        lrc_color_purple.setOnClickListener(this);
    }

    /**
     * 设置高亮画笔颜色
     *
     * @param color 颜色
     */
    private void setPaintHLColor(int color) {
        int[] colors = new int[]{color, color};
        krcView.setPaintHLColor(colors, true);
    }

    /**
     * 初始化播放设置视图
     */
    private void initPlaySetView() {
        if (application.appSet.getCurrentMusic()==null)return;
        View set = View.inflate(this, R.layout.play_set_view, null);
        Button play_set_close_button = set.findViewById(R.id.play_set_close_button);
        play_set_close_button.setOnClickListener(this);
        TextView play_set_music_title = set.findViewById(R.id.play_set_music_title);
        play_set_music_title.setText(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName() + "-" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
        play_volume = set.findViewById(R.id.play_volume);
        ImageView chakangeshou=set.findViewById(R.id.chakangeshou);
        chakangeshou.setOnClickListener(this);
        String avatar = application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthors().get(0).getAvatar();
        ImageUtils.setImageCircle(this,chakangeshou,avatar);
        ImageView chakanzhuanji=set.findViewById(R.id.chakanzhuanji);
        String image = application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg();
        ImageUtils.setImageCircle(this,chakanzhuanji,image);
        chakanzhuanji.setOnClickListener(this);
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);// 3
        current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        play_volume.setMax(max);
        play_volume.setProgress(current);
        play_volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        myDialog = new MyDialog(set, this, R.style.NormalDialogStyle);
    }

    /**
     * 切换播放模式，点击播放模式时执行该方法
     */
    private void playMode() {
        switch (application.appSet.getPlayMode()) {
            case SingleCycle:
                playMode.setImageResource(R.drawable.list);
                application.appSet.setPlayMode(MusicPlayMode.ListLoop);
                Toast.makeText(this, "列表循环", Toast.LENGTH_SHORT).show();
                break;
            case ListLoop:
                playMode.setImageResource(R.drawable.ran);
                application.appSet.setPlayMode(MusicPlayMode.ShufflePlayback);
                Toast.makeText(this, "随机播放", Toast.LENGTH_SHORT).show();
                break;
            case ShufflePlayback:
                playMode.setImageResource(R.drawable.one);
                application.appSet.setPlayMode(MusicPlayMode.SingleCycle);
                Toast.makeText(this, "单曲循环", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 收藏歌曲，点击收藏时执行该方法
     */
    private void like() {
        List<MusicInfo> collect = application.appSet.getCollect();
        if (collect == null) collect = new ArrayList<>();
        if (application.appSet.getCurrentMusic().isLike()) {
            application.appSet.getCurrentMusic().setLike(false);
            like.setImageResource(R.drawable.unlike);
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
            collect.remove(application.appSet.getCurrentMusic());
        } else {
            application.appSet.getCurrentMusic().setLike(true);
            like.setImageResource(R.drawable.like);
            Toast.makeText(this, "已收藏", Toast.LENGTH_SHORT).show();
            collect.add(application.appSet.getCurrentMusic());
        }
        application.appSet.setCollect(collect);
        musicControl.update();
    }

    /**
     * 播放mv，点击mv时执行该方法
     */
    private void playmv() {
        if (application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHaveMv() == 1) {
            String mvHash = application.appSet.getCurrentMusic().getMvHash();
            String key = DigestUtils.md5Hex(mvHash + "kugoumvcloud");
            URL url = null;
            try {
                url = new URL("http://trackermv.kugou.com/interface/index/cmd=100&hash=" + mvHash +
                        "&key=" + key + "&pid=6&ext=mp4&ismp3=0");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpUtil.sendGetRequest(url, handler, MV_PLAY_URL);
        } else {
            Toast.makeText(PlayActivity.this, "该歌曲暂时没有mv", Toast.LENGTH_SHORT).show();
        }
    }
}