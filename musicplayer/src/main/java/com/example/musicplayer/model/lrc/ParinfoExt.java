
package com.example.musicplayer.model.lrc;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("unused")
public class ParinfoExt implements Serializable {

    @Expose
    private Long entry;

    public Long getEntry() {
        return entry;
    }

    public void setEntry(Long entry) {
        this.entry = entry;
    }

}
