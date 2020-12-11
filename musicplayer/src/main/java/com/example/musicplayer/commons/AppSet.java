package com.example.musicplayer.commons;

import com.example.musicplayer.model.mv.MV;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.pojo.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author 章可政
 * @date 2020/12/5 22:31
 */
public class AppSet implements Serializable {
    private int lrc_set_lrc_size = 50;//歌词字体大小
    private String lrc_set_lrc_color = "红色";//歌词字体颜色
    private String lrc_set_lrc_font = "";//歌词字体
    private boolean enlarge = false;//true歌词放大，false歌词不放大
    private boolean lineByLine = false;//true歌词逐行放大，false歌词逐字放大
    private MusicInfo currentMusic;//当前所播放的音乐
    private List<MusicInfo> musicInfos;//音乐列表
    private int currentPlayPosition = -1;//当前音乐播放位置
    private String currentMode = "列表循环";
    private User user;//用户
    private List<MV> mvList;//用于存放曾经播放的mv视频
    private List<MusicInfo> recentPlay;
    private List<MusicInfo>collect;

    public int getLrc_set_lrc_size() {
        return lrc_set_lrc_size;
    }

    public void setLrc_set_lrc_size(int lrc_set_lrc_size) {
        this.lrc_set_lrc_size = lrc_set_lrc_size;
    }

    public String getLrc_set_lrc_color() {
        return lrc_set_lrc_color;
    }

    public void setLrc_set_lrc_color(String lrc_set_lrc_color) {
        this.lrc_set_lrc_color = lrc_set_lrc_color;
    }

    public String getLrc_set_lrc_font() {
        return lrc_set_lrc_font;
    }

    public void setLrc_set_lrc_font(String lrc_set_lrc_font) {
        this.lrc_set_lrc_font = lrc_set_lrc_font;
    }

    public boolean isEnlarge() {
        return enlarge;
    }

    public void setEnlarge(boolean enlarge) {
        this.enlarge = enlarge;
    }

    public boolean isLineByLine() {
        return lineByLine;
    }

    public void setLineByLine(boolean lineByLine) {
        this.lineByLine = lineByLine;
    }

    public MusicInfo getCurrentMusic() {
        return currentMusic;
    }

    public void setCurrentMusic(MusicInfo currentMusic) {
        this.currentMusic = currentMusic;
    }

    public List<MusicInfo> getMusicInfos() {
        return musicInfos;
    }

    public void setMusicInfos(List<MusicInfo> musicInfos) {
        this.musicInfos = musicInfos;
    }

    public int getCurrentPlayPosition() {
        return currentPlayPosition;
    }

    public void setCurrentPlayPosition(int currentPlayPosition) {
        this.currentPlayPosition = currentPlayPosition;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MV> getMvList() {
        return mvList;
    }

    public void setMvList(List<MV> mvList) {
        this.mvList = mvList;
    }

    public List<MusicInfo> getRecentPlay() {
        return recentPlay;
    }

    public void setRecentPlay(List<MusicInfo> recentPlay) {
        this.recentPlay = recentPlay;
    }

    public List<MusicInfo> getCollect() {
        return collect;
    }

    public void setCollect(List<MusicInfo> collect) {
        this.collect = collect;
    }
}