
package com.example.musicplayer.model.lrc;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Candidate implements Serializable {

    @Expose
    private String accesskey;
    @Expose
    private Long adjust;
    @SerializedName("can_score")
    private Boolean canScore;
    @Expose
    private Long contenttype;
    @Expose
    private Long duration;
    @Expose
    private Long hitlayer;
    @Expose
    private String id;
    @Expose
    private Long krctype;
    @Expose
    private String language;
    @SerializedName("lyric_comment_mark")
    private List<Object> lyricCommentMark;
    @Expose
    private String nickname;
    @Expose
    private String originame;
    @Expose
    private String origiuid;
    @Expose
    private transient List<List<Long>> parinfo;
    @Expose
    private  List<ParinfoExt> parinfoExt;
    @SerializedName("product_from")
    private String productFrom;
    @Expose
    private Long score;
    @Expose
    private String singer;
    @Expose
    private String song;
    @Expose
    private String soundname;
    @Expose
    private String sounduid;
    @Expose
    private String transname;
    @Expose
    private String transuid;
    @Expose
    private String uid;

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public Long getAdjust() {
        return adjust;
    }

    public void setAdjust(Long adjust) {
        this.adjust = adjust;
    }

    public Boolean getCanScore() {
        return canScore;
    }

    public void setCanScore(Boolean canScore) {
        this.canScore = canScore;
    }

    public Long getContenttype() {
        return contenttype;
    }

    public void setContenttype(Long contenttype) {
        this.contenttype = contenttype;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getHitlayer() {
        return hitlayer;
    }

    public void setHitlayer(Long hitlayer) {
        this.hitlayer = hitlayer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getKrctype() {
        return krctype;
    }

    public void setKrctype(Long krctype) {
        this.krctype = krctype;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Object> getLyricCommentMark() {
        return lyricCommentMark;
    }

    public void setLyricCommentMark(List<Object> lyricCommentMark) {
        this.lyricCommentMark = lyricCommentMark;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOriginame() {
        return originame;
    }

    public void setOriginame(String originame) {
        this.originame = originame;
    }

    public String getOrigiuid() {
        return origiuid;
    }

    public void setOrigiuid(String origiuid) {
        this.origiuid = origiuid;
    }

    public List<List<Long>> getParinfo() {
        return parinfo;
    }

    public void setParinfo(List<List<Long>> parinfo) {
        this.parinfo = parinfo;
    }

    public List<ParinfoExt> getParinfoExt() {
        return parinfoExt;
    }

    public void setParinfoExt(List<ParinfoExt> parinfoExt) {
        this.parinfoExt = parinfoExt;
    }

    public String getProductFrom() {
        return productFrom;
    }

    public void setProductFrom(String productFrom) {
        this.productFrom = productFrom;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSoundname() {
        return soundname;
    }

    public void setSoundname(String soundname) {
        this.soundname = soundname;
    }

    public String getSounduid() {
        return sounduid;
    }

    public void setSounduid(String sounduid) {
        this.sounduid = sounduid;
    }

    public String getTransname() {
        return transname;
    }

    public void setTransname(String transname) {
        this.transname = transname;
    }

    public String getTransuid() {
        return transuid;
    }

    public void setTransuid(String transuid) {
        this.transuid = transuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
