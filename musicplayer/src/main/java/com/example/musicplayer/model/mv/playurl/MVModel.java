
package com.example.musicplayer.model.mv.playurl;

import com.example.musicplayer.model.mv.playurl.Mvdata;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class MVModel implements Serializable {

    @SerializedName("mvdata")
    private Mvdata mMvdata;
    @SerializedName("singer")
    private String mSinger;
    @SerializedName("songname")
    private String mSongname;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("track")
    private Long mTrack;
    @SerializedName("type")
    private Long mType;

    public Mvdata getMvdata() {
        return mMvdata;
    }

    public void setMvdata(Mvdata mvdata) {
        mMvdata = mvdata;
    }

    public String getSinger() {
        return mSinger;
    }

    public void setSinger(String singer) {
        mSinger = singer;
    }

    public String getSongname() {
        return mSongname;
    }

    public void setSongname(String songname) {
        mSongname = songname;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getTrack() {
        return mTrack;
    }

    public void setTrack(Long track) {
        mTrack = track;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

}
