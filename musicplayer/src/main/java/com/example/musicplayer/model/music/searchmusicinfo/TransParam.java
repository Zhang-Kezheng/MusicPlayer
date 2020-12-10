
package com.example.musicplayer.model.music.searchmusicinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class TransParam implements Serializable {

    @SerializedName("appid_block")
    private String appidBlock;
    @Expose
    private Long cid;
    @SerializedName("cpy_grade")
    private Long cpyGrade;
    @SerializedName("cpy_level")
    private Long cpyLevel;
    @Expose
    private Long display;
    @SerializedName("display_rate")
    private Long displayRate;
    @SerializedName("hash_offset")
    private HashOffset hashOffset;
    @SerializedName("musicpack_advance")
    private Long musicpackAdvance;
    @SerializedName("pay_block_tpl")
    private Long payBlockTpl;

    public String getAppidBlock() {
        return appidBlock;
    }

    public void setAppidBlock(String appidBlock) {
        this.appidBlock = appidBlock;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCpyGrade() {
        return cpyGrade;
    }

    public void setCpyGrade(Long cpyGrade) {
        this.cpyGrade = cpyGrade;
    }

    public Long getCpyLevel() {
        return cpyLevel;
    }

    public void setCpyLevel(Long cpyLevel) {
        this.cpyLevel = cpyLevel;
    }

    public Long getDisplay() {
        return display;
    }

    public void setDisplay(Long display) {
        this.display = display;
    }

    public Long getDisplayRate() {
        return displayRate;
    }

    public void setDisplayRate(Long displayRate) {
        this.displayRate = displayRate;
    }

    public HashOffset getHashOffset() {
        return hashOffset;
    }

    public void setHashOffset(HashOffset hashOffset) {
        this.hashOffset = hashOffset;
    }

    public Long getMusicpackAdvance() {
        return musicpackAdvance;
    }

    public void setMusicpackAdvance(Long musicpackAdvance) {
        this.musicpackAdvance = musicpackAdvance;
    }

    public Long getPayBlockTpl() {
        return payBlockTpl;
    }

    public void setPayBlockTpl(Long payBlockTpl) {
        this.payBlockTpl = payBlockTpl;
    }

}
