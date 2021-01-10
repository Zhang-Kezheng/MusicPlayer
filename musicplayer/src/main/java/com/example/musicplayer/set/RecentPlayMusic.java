package com.example.musicplayer.set;

import org.litepal.crud.LitePalSupport;
import com.example.musicplayer.model.music.searchmusicplayurl.*;
/**
 * @author 章可政
 * @date 2021/1/8 1:23
 */
public class RecentPlayMusic extends LitePalSupport {
    private String mvHash;
    private boolean isLike;
    private SearchMusicPlayUrlData musicPlayUrlData;

    public String getMvHash() {
        return mvHash;
    }

    public void setMvHash(String mvHash) {
        this.mvHash = mvHash;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData getMusicPlayUrlData() {
        return musicPlayUrlData;
    }

    public void setMusicPlayUrlData(com.example.musicplayer.model.music.searchmusicplayurl.SearchMusicPlayUrlData musicPlayUrlData) {
        this.musicPlayUrlData = musicPlayUrlData;
    }
}
