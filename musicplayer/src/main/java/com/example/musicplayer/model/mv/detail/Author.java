
package com.example.musicplayer.model.mv.detail;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Author implements Serializable {

    @Expose
    private String singeravatar;
    @Expose
    private Long singerid;
    @Expose
    private String singername;

    public String getSingeravatar() {
        return singeravatar;
    }

    public void setSingeravatar(String singeravatar) {
        this.singeravatar = singeravatar;
    }

    public Long getSingerid() {
        return singerid;
    }

    public void setSingerid(Long singerid) {
        this.singerid = singerid;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

}
