
package com.example.musicplayer.model.music.searchmusicinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class SectagInfo implements Serializable {

    @SerializedName("is_sectag")
    private Long isSectag;

    public Long getIsSectag() {
        return isSectag;
    }

    public void setIsSectag(Long isSectag) {
        this.isSectag = isSectag;
    }

}
