
package com.example.musicplayer.model.mv.detail;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("unused")
public class VideoScale implements Serializable {

    @Expose
    private Long height;
    @Expose
    private Long width;

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

}
