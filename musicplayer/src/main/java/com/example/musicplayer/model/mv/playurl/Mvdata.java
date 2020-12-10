
package com.example.musicplayer.model.mv.playurl;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Mvdata implements Serializable {

    @SerializedName("hd")
    private Hd mHd;
    @SerializedName("rq")
    private Rq mRq;
    @SerializedName("sd")
    private Sd mSd;
    @SerializedName("sq")
    private Sq mSq;

    public Hd getHd() {
        return mHd;
    }

    public void setHd(Hd hd) {
        mHd = hd;
    }

    public Rq getRq() {
        return mRq;
    }

    public void setRq(Rq rq) {
        mRq = rq;
    }

    public Sd getSd() {
        return mSd;
    }

    public void setSd(Sd sd) {
        mSd = sd;
    }

    public Sq getSq() {
        return mSq;
    }

    public void setSq(Sq sq) {
        mSq = sq;
    }

}
