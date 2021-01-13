package com.example.musicplayer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.musicplayer.R;
import com.example.musicplayer.commons.AppSet;
import com.example.musicplayer.commons.MusicPlayerApplication;
import com.example.musicplayer.model.user.MusicInfo;
import org.litepal.LitePal;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
    private final String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final List<String> mPermissionList = new ArrayList<>();
    private static final int PERMISSION_REQUEST = 1;
    private MusicPlayerApplication application;//当前应用
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_actvity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        application = (MusicPlayerApplication) getApplication();
        player = MediaPlayer.create(this, R.raw.login);
        player.start();
        player.setOnCompletionListener(mp -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checkPermission();
            initAppParam();
        });
        player.setOnErrorListener((mp, what, extra) -> true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player = null;
    }

    private void startMainActivity() {
        Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(mainIntent);
        WelcomeActivity.this.finish();
    }

    /**
     * 检查所需权限，如没有授予，申请授权
     */
    private void checkPermission() {
        mPermissionList.clear();
        //判断哪些权限未授予
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (!mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST);
        } else {
            checkFile();//检查文件
            startMainActivity();
        }
    }

    /**
     * 请求授权的回调，如果权限请求成功，则会进行检查文件
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            checkFile();//检查文件
            initAppParam();
            startMainActivity();
        } else {
            Toast.makeText(this, "没有同意授权,请同意授权！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 检查文件是否存在，如不存在则创建
     */
    private void checkFile() {

        // 创建MusicPlayer根文件夹
        File CONFIG_PATH = new File(MusicPlayerApplication.CONFIG_PATH);
        if (!CONFIG_PATH.exists()) {
            CONFIG_PATH.mkdir();
        }


        //创建存放音乐的文件夹
        File MUSIC_PATH = new File(MusicPlayerApplication.MUSIC_PATH);
        if (!MUSIC_PATH.exists()) {
            MUSIC_PATH.mkdir();
        }

        //创建存放歌词文件的文件夹
        File LRC_PATH = new File(MusicPlayerApplication.LRC_PATH);
        if (!LRC_PATH.exists()) {
            LRC_PATH.mkdir();
        }

        //创建存放应用的参数的配置文件
        File appSet = new File(MusicPlayerApplication.CONFIG_PATH + "appSet.conf");
        if (!appSet.exists()) {
            try {
                appSet.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化全局参数
     */
    private void initAppParam() {
        //初始化应用的配置信息
        File file4 = new File(MusicPlayerApplication.CONFIG_PATH + "appSet.conf");

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file4));
            application.appSet = (AppSet) objectInputStream.readObject();
            if (application.appSet == null) {
                application.appSet = new AppSet();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file4));
                objectOutputStream.writeObject(application.appSet);
                objectOutputStream.close();
            }
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            if (application.appSet == null) {
                application.appSet = new AppSet();
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file4));
                    objectOutputStream.writeObject(application.appSet);
                    objectOutputStream.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        Map<String, List<MusicInfo>> songList = application.appSet.getSongList();
        if (songList == null) {
            songList = new HashMap<>();
            songList.put("最近播放", application.appSet.getRecentPlay());
            songList.put("我喜欢", application.appSet.getCollect());
            application.appSet.setSongList(songList);
        }
        switch (application.appSet.getCurrentSongList()) {
            case "最近播放":
                application.musicInfos = songList.get("最近播放");
                break;
            case "我喜欢":
                application.musicInfos = songList.get("我喜欢");
                break;
            default:
                break;
        }
    }

}
