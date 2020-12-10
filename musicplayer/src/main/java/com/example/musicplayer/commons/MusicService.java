package com.example.musicplayer.commons;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.*;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.bumptech.glide.Glide;
import com.example.musicplayer.Observer;
import com.example.musicplayer.R;
import com.example.musicplayer.Subject;
import com.example.musicplayer.activity.HomePageActivity;
import com.example.musicplayer.activity.PlayActivity;
import com.example.musicplayer.fragment.RecentFragment;
import com.example.musicplayer.model.lrc.GetLyricsData;
import com.example.musicplayer.model.lrc.SearchLyricsData;
import com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.util.HttpUtil;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static com.example.musicplayer.activity.PlayActivity.krcView;
import static com.example.musicplayer.commons.MusicPlayerApplication.*;


public class MusicService extends Service implements Subject ,Observer{
    private MediaPlayer player;//音乐播放器
    private static MusicPlayerApplication application;//当前应用
    public static MusicControl musicControl;//音乐控制器
    private PlayActivity activity;
    private boolean running;
    private final Vector<com.example.musicplayer.Observer> observers = new Vector<>();
    private NotificationManager manager;
    private RemoteViews remoteViews;
    private Notification notification;
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

    //初始化配置
    @Override
    public void onCreate() {
        super.onCreate();
        application = (MusicPlayerApplication) getApplication();
        player = new MediaPlayer();
        player.setOnCompletionListener(mp -> {
            Log.i("HB", "播放完成" + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAudioName());
            musicControl.next();

        });
        player.setOnErrorListener((mp, what, extra) -> true);
        player.setOnPreparedListener(mp -> {
            mp.start();
            running = true;
            addTimer();
            application.isPlaying=true;
            if (application.isFirst) {
                mp.pause();
                application.isPlaying=false;
                application.isFirst=false;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //绑定
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
         musicControl = new MusicControl();
        if (application.appSet.getCurrentPlayPosition() != -1) {
            musicControl.changeMusic(application.appSet.getCurrentPlayPosition());
        }
        //创建一个获取网络图片的线程
            new Thread(() -> {
                Bitmap bitmap= null;
                if (application.appSet.getCurrentPlayPosition() != -1) {
                try {
                        bitmap = BitmapFactory.decodeStream(new URL(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg()).openConnection().getInputStream());
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
                initNotification(bitmap);
            }).start();
        observers.add(this);
        return musicControl;
    }

    /**
     * 初始化通知界面
     * @param bitmap 歌手图片
     */
    private void initNotification(Bitmap bitmap){
        String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
        String CHANNEL_ONE_NAME = "CHANNEL_ONE_ID";
        //创建通知频道
        NotificationChannel notificationChannel;
        notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        //实例化通知管理器
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //添加通知频道
        manager.createNotificationChannel(notificationChannel);
        //实例化自定义通知视图
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.notification_layout);
        if (bitmap!=null) {
            remoteViews.setImageViewBitmap(R.id.notification_bg, bitmap);
            remoteViews.setTextViewText(R.id.notification_singerName,application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
            remoteViews.setTextViewText(R.id.notification_songName,application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName());
        }
        //添加去播放页面的意图
        Intent intent= new Intent(this, PlayActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //给通知上的播放按钮添加意图
        Intent intentPlay = new Intent("play");//新建意图，并设置action标记为"play"，用于接收广播时过滤意图信息
        PendingIntent pIntentPlay = PendingIntent.getBroadcast(this, 0,
                intentPlay, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_pause, pIntentPlay);//为play控件注册事件
        //给通知上的下一首按钮添加意图
        Intent intentNext = new Intent("next");
        PendingIntent pIntentNext = PendingIntent.getBroadcast(this, 0,
                intentNext, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_next, pIntentNext);
        //给通知上的上一首按钮添加意图
        Intent intentPre = new Intent("pre");
        PendingIntent pIntentPre = PendingIntent.getBroadcast(this, 0,
                intentPre, 0);
        remoteViews.setOnClickPendingIntent(R.id.notification_pre, pIntentPre);
        //初始化通知
        notification = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID)
                .setTicker("Nature")
                .setSmallIcon(R.mipmap.logo)
                .setContentIntent(pendingIntent)
                .setCustomBigContentView(remoteViews)
                .setCustomContentView(remoteViews)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        //将本服务设置前台服务，服务设置为前台时必须依托于通知
        startForeground(1, notification);
        manager.notify(1, notification);//开启通知
    }
    //销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player == null) return;
        if (player.isLooping()) player.stop();
        player.release();
        player = null;
    }

    /**
     * 添加计时器，用于定时向播放页面发送当前歌曲所在时间
     */
    public void addTimer() {
        new Thread(() -> {
            while (true) {
                if (player != null && application.appSet.getCurrentPlayPosition() != -1 && krcView != null && activity != null) {
                    krcView.seekto(player.getCurrentPosition());
                    krcView.postInvalidate();
                    Message message = activity.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", player.getDuration());
                    bundle.putInt("currentDuration", player.getCurrentPosition());
                    message.setData(bundle);
                    activity.handler.sendMessage(message);
                    if (!running) {
                        break;
                    }
                }
            }
        }).start();
    }

    /**
     * 观察者模式，通过广播来实现更改所有需要修改的视图的内容
     * @param command 命令
     */
    @Override
    public void update(int command) {
        switch (command){
            case PAUSE:
                remoteViews.setImageViewResource(R.id.notification_pause,R.drawable.play);
                manager.notify(1, notification);
                break;
            case PLAY:
                remoteViews.setImageViewResource(R.id.notification_pause,R.drawable.pause);
                manager.notify(1, notification);
                break;
            case UPDATE_UI:
                remoteViews.setTextViewText(R.id.notification_songName,application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getSongName());
                remoteViews.setTextViewText(R.id.notification_singerName,application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getAuthorName());
                new Thread(() -> {
                    Bitmap bitmap= null;
                    try {
                        bitmap = BitmapFactory.decodeStream(new URL(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getImg()).openConnection().getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    remoteViews.setImageViewBitmap(R.id.notification_bg,bitmap);
                    manager.notify(1, notification);
                }).start();
                break;
        }
    }

    /**
     * 按钮广播接收，用来接收通知传递过来的意图，用于改变服务状态
     */
    public static  class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case "play":
                    musicControl.play();
                    break;
                case "next":
                    musicControl.next();
                    break;
                case "pre":
                    musicControl.pre();
                    break;
            }
        }

    }
    //音乐控制器
    public class MusicControl extends Binder {
        public Subject getSubject() {
            return MusicService.this;
        }

        public void setActivity(PlayActivity playActivity) {
            activity = playActivity;
        }

        //切换音乐
        public void changeMusic(int position) {
            player.stop();
            running = false;
            player.reset();
            application.appSet.setCurrentMusic(application.appSet.getMusicInfos().get(position));
            application.appSet.setCurrentPlayPosition(position);
            List<MusicInfo> recentPlay = application.appSet.getRecentPlay();
            if (recentPlay==null) recentPlay=new ArrayList<>();
            if (recentPlay.size()==0){
                recentPlay.add(0,application.appSet.getCurrentMusic());
            }else {
                boolean flag=false;
                for (MusicInfo musicInfo : recentPlay) {
                    if (application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash().equals(musicInfo.getMusicPlayUrlData().getData().getHash())){
                        flag=true;
                        break;
                    }
                }
                if (flag){
                    recentPlay.remove(application.appSet.getCurrentMusic());
                }
                recentPlay.add(0,application.appSet.getCurrentMusic());
            }
            application.appSet.setRecentPlay(recentPlay);
            try {
                boolean b = checkKrc(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash());
                if (!b) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        downLoadLrc(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash());
                    }
                }
                if (!checkMusic(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash())) {
                    downLoadMusic();
                    player.setDataSource(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getPlayUrl());
                } else {
                                      player.setDataSource(MusicPlayerApplication.MUSIC_PATH + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash() + ".mp3");
                }
                player.prepareAsync();
                notifyObservers(CHANGE_LRC);
                notifyObservers(PLAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notifyObservers(UPDATE_UI);
            MusicPlayerApplication.serialization(application.appSet,MusicPlayerApplication.CONFIG_PATH+"appSet.conf");
        }
        public void update(){
            notifyObservers(UPDATE_UI);
        }
        /**
         * 播放
         */
        public void play() {
            if (player.isPlaying()) {
                player.pause();
                notifyObservers(PAUSE);
                application.isPlaying=false;
            } else {
                player.start();
                notifyObservers(PLAY);
                application.isPlaying=true;
            }
            notifyObservers(UPDATE_UI);
        }

        /**
         * 下一首
         */
        public void next() {
            switch (application.appSet.getCurrentMode()) {
                case "随机播放":
                    Random random = new Random();
                    application.appSet.setCurrentPlayPosition( random.nextInt(application.appSet.getMusicInfos().size()));
                    changeMusic(application.appSet.getCurrentPlayPosition());
                    break;
                case "列表循环":
                    application.appSet.setCurrentPlayPosition(application.appSet.getCurrentPlayPosition()+1);
                    if (application.appSet.getCurrentPlayPosition() > application.appSet.getMusicInfos().size() - 1) {
                        application.appSet.setCurrentPlayPosition(0);
                    }
                    changeMusic(application.appSet.getCurrentPlayPosition());
                    break;
                case "单曲循环":
                    changeMusic(application.appSet.getCurrentPlayPosition());
                    break;
            }
            notifyObservers(NEXT);
        }

        /**
         * 上一首
         */
        public void pre() {
            switch (application.appSet.getCurrentMode()) {
                case "随机播放":
                    Random random = new Random();
                    application.appSet.setCurrentPlayPosition(random.nextInt(application.appSet.getMusicInfos().size()));
                    changeMusic(application.appSet.getCurrentPlayPosition());
                    break;
                case "列表循环":
                    application.appSet.setCurrentPlayPosition(application.appSet.getCurrentPlayPosition()-1);
                    if (application.appSet.getCurrentPlayPosition() < 0) {
                        application.appSet.setCurrentPlayPosition(application.appSet.getMusicInfos().size()-1);
                    }
                    changeMusic(application.appSet.getCurrentPlayPosition());
                    break;
            }
            notifyObservers(PRE);
        }

        //移动播放位置
        public void seekTo(int progress) {
            if (player == null) return;
            player.seekTo(progress);
        }
    }

    /**
     * 检查本地是否存在该歌曲，如果不存在则进行下载
     *
     * @param hash 歌曲的hash值
     * @return 歌曲是否在本地存在
     */
    private boolean checkMusic(String hash) {
        File file = new File(MusicPlayerApplication.MUSIC_PATH + hash + ".mp3");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 检查本地是否存在该歌词文件，如果不存在则进行下载到本地，如果存在则进行读取本地歌词
     *
     * @param hash 歌曲的hash值
     * @return 歌词是否在本地存在
     */
    private boolean checkKrc(String hash) {
        File file = new File(MusicPlayerApplication.LRC_PATH + hash + ".krc");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 下载歌曲
     */
    private void downLoadMusic() {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getPlayUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Cookie", "kg_mid=9cc69b78794df8550f3c29b2d142ac46");
                conn.setConnectTimeout(6 * 1000);
                conn.setReadTimeout(6 * 1000);
                InputStream in = conn.getInputStream();
                File file = new File(MusicPlayerApplication.MUSIC_PATH + application.appSet.getCurrentMusic().getMusicPlayUrlData().getData().getHash() + ".mp3");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bytes = new byte[1024];
                int len;
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, len);
                }
                fileOutputStream.close();
                bufferedInputStream.close();
                in.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    /**
     * 下载歌词
     *
     * @param hash 歌曲的hash值
     * @throws IOException
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void downLoadLrc(String hash) throws IOException {
        URL url = new URL("http://krcs.kugou.com/search?ver=1&man=yes&client=mobi&keyword=&duration=&hash=" + hash + "&album_audio_id=");
        String data = HttpUtil.sendGetRequest(url);
        Gson gson = new Gson();
        SearchLyricsData searchLyricsData = gson.fromJson(data, SearchLyricsData.class);
        String id = searchLyricsData.getCandidates().get(0).getId();
        String accesskey = searchLyricsData.getCandidates().get(0).getAccesskey();
        url = new URL("http://lyrics.kugou.com/download?ver=1&client=pc&id=" + id + "&accesskey=" + accesskey + "&fmt=krc&charset=utf8");
        data = HttpUtil.sendGetRequest(url);
        GetLyricsData getLyricsData = gson.fromJson(data, GetLyricsData.class);
        byte[] decode = Base64.getDecoder().decode(getLyricsData.getContent());
        File file = new File(MusicPlayerApplication.LRC_PATH + hash + ".krc");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(decode);
        fileOutputStream.close();
    }
}
