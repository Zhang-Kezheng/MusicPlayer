package com.example.musicplayer.commons;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.example.musicplayer.Observer;
import com.example.musicplayer.Subject;

public class MusicServiceConnect implements ServiceConnection {
    private MusicService.MusicControl musicControl;
    private Subject subject;
    private Observer activity;

    public void setActivity(Observer activity) {
        this.activity = activity;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        musicControl=(MusicService.MusicControl)service;
        subject = musicControl.getSubject();
        subject.registerObserver(activity);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        subject.removeObserver(activity);
    }

    public MusicService.MusicControl getMusicControl() {
        return musicControl;
    }

}
