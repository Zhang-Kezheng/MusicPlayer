package com.example.musicplayer.commons;


import android.graphics.Color;
import com.example.musicplayer.model.mv.MV;
import com.example.musicplayer.model.user.MusicInfo;
import com.example.musicplayer.set.ApplicationTypeFace;
import com.example.musicplayer.set.MusicPlayMode;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 章可政
 * @date 2020/12/5 22:31
 */
public class AppSet  extends LitePalSupport implements Serializable {
    private int lrc_set_lrc_size = 50;//歌词字体大小
    private String lrc_set_lrc_color = "红色";//歌词字体颜色
    private String lrc_set_lrc_font = "";//歌词字体
    private boolean enlarge = false;//true歌词放大，false歌词不放大
    private boolean lineByLine = false;//true歌词逐行放大，false歌词逐字放大
    private MusicInfo currentMusic;//当前所播放的音乐
    private int currentPlayPosition = -1;//当前音乐播放位置
    private MusicPlayMode playMode = MusicPlayMode.ListLoop;
    private List<MV> mvList=new ArrayList<>();//用于存放曾经播放的mv视频
    private List<MusicInfo> recentPlay=new ArrayList<>();//最近播放
    private List<MusicInfo>collect=new ArrayList<>();//收藏
    private Map<String,List<MusicInfo>> songList;//歌单，默认存在我喜欢歌单和最近播放歌单
    private String currentSongList="最近播放";
    private List<String> search_history=new ArrayList<>();//播放历史
    private ApplicationTypeFace typeFace=ApplicationTypeFace.DEFAULT;//歌词字体
    private int lrc_color= Color.RED;//歌词颜色

    public int getLrc_color() {
        return lrc_color;
    }

    public void setLrc_color(int lrc_color) {
        this.lrc_color = lrc_color;
    }

    public ApplicationTypeFace getTypeFace() {
        return typeFace;
    }

    public void setTypeFace(ApplicationTypeFace typeFace) {
        this.typeFace = typeFace;
    }

    public List<String> getSearch_history() {
        return search_history;
    }

    public void setSearch_history(List<String> search_history) {
        this.search_history = search_history;
    }

    public Map<String, List<MusicInfo>> getSongList() {
        return songList;
    }

    public void setSongList(Map<String, List<MusicInfo>> songList) {
        this.songList = songList;
    }

    public String getCurrentSongList() {
        return currentSongList;
    }

    public void setCurrentSongList(String currentSongList) {
        this.currentSongList = currentSongList;
    }

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


    public int getCurrentPlayPosition() {
        return currentPlayPosition;
    }

    public void setCurrentPlayPosition(int currentPlayPosition) {
        this.currentPlayPosition = currentPlayPosition;
    }


    public MusicPlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(MusicPlayMode playMode) {
        this.playMode = playMode;
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
