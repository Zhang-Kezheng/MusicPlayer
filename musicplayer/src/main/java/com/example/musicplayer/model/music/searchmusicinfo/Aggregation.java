
package com.example.musicplayer.model.music.searchmusicinfo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Aggregation implements Serializable {

    @Expose
    private Long count;
    @Expose
    private String key;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
