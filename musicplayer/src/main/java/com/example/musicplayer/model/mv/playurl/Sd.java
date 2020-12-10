
package com.example.musicplayer.model.mv.playurl;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Sd implements Serializable {

    @SerializedName("bitrate")
    private Long mBitrate;
    @SerializedName("downurl")
    private String mDownurl;
    @SerializedName("filesize")
    private Long mFilesize;
    @SerializedName("hash")
    private String mHash;
    @SerializedName("timelength")
    private Long mTimelength;

    public Long getBitrate() {
        return mBitrate;
    }

    public void setBitrate(Long bitrate) {
        mBitrate = bitrate;
    }

    public String getDownurl() {
        return mDownurl;
    }

    public void setDownurl(String downurl) {
        mDownurl = downurl;
    }

    public Long getFilesize() {
        return mFilesize;
    }

    public void setFilesize(Long filesize) {
        mFilesize = filesize;
    }

    public String getHash() {
        return mHash;
    }

    public void setHash(String hash) {
        mHash = hash;
    }

    public Long getTimelength() {
        return mTimelength;
    }

    public void setTimelength(Long timelength) {
        mTimelength = timelength;
    }

}
