package com.example.musicplayer.model.mv.greatcount;

import java.io.Serializable;

/**
 * @author 章可政
 * @date 2020/10/30 23:53
 */
public class GreatStatus implements Serializable {
    private int likenum;
    private int count;
    private boolean haslike;
    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }
    public int getLikenum() {
        return likenum;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }

    public void setHaslike(boolean haslike) {
        this.haslike = haslike;
    }
    public boolean getHaslike() {
        return haslike;
    }

}
