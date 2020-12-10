
package com.example.musicplayer.model.mv.comment;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Like implements Serializable {

    @Expose
    private Long count;
    @Expose
    private Boolean haslike;
    @Expose
    private Long likenum;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getHaslike() {
        return haslike;
    }

    public void setHaslike(Boolean haslike) {
        this.haslike = haslike;
    }

    public Long getLikenum() {
        return likenum;
    }

    public void setLikenum(Long likenum) {
        this.likenum = likenum;
    }

}
