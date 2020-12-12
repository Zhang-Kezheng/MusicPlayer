package com.example.musicplayer.commons;

import android.annotation.SuppressLint;
import android.app.Application;
import androidx.multidex.MultiDex;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MusicPlayerApplication extends Application {
    public AppSet appSet;
    public boolean isFirst = true;//是否第一次播放，默认为true
    public boolean isPlaying;//是否在播放音乐，默认false
    @SuppressLint("SdCardPath")
    public static final String LRC_PATH = "/mnt/sdcard/MusicPlayer/lrc/";
    @SuppressLint("SdCardPath")
    public static final String CONFIG_PATH = "/mnt/sdcard/MusicPlayer/";
    @SuppressLint("SdCardPath")
    public static final String MUSIC_PATH = "/mnt/sdcard/MusicPlayer/music/";
    public static final int SEARCH_MUSIC_KEYWORD = 1;
    public static final int SEARCH_MUSIC_PLAY_URL = 2;
    public static final int MV_PLAY_URL = 3;
    public static final int MV_DETAIL = 4;
    public final static int PLAY=-1;
    public final static int PAUSE=-2;
    public final static int NEXT=-3;
    public final static int PRE=-4;
    public final static int UPDATE_UI=-5;
    public final static int CHANGE_LRC=-6;
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
    public static void serialization(AppSet appSet,String path){
        File file =new File(path);
        try {
            ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(appSet);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
