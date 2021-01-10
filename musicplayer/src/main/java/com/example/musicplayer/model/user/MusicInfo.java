package com.example.musicplayer.model.user;


import com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData;

import java.io.Serializable;

/**
 * @author 章可政
 * @date 2020/10/29 12:47
 */
public class MusicInfo implements Serializable {
    private String mvHash;
    private SearchMusicPlayUrlData musicPlayUrlData;
    private boolean isLike;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getMvHash() {
        return mvHash;
    }

    public void setMvHash(String mvHash) {
        this.mvHash = mvHash;
    }

    public SearchMusicPlayUrlData getMusicPlayUrlData() {
        return musicPlayUrlData;
    }

    public void setMusicPlayUrlData(SearchMusicPlayUrlData musicPlayUrlData) {
        this.musicPlayUrlData = musicPlayUrlData;
    }

    public MusicInfo(String mvHash, SearchMusicPlayUrlData musicPlayUrlData) {
        this.mvHash = mvHash;
        this.musicPlayUrlData = musicPlayUrlData;
    }

    public MusicInfo() {
    }
}
