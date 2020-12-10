
package com.example.musicplayer.model.mv.detail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class Info implements Serializable {

    @SerializedName("audio_info")
    private AudioInfo audioInfo;
    @Expose
    private List<Author> authors;
    @SerializedName("collect_count")
    private Long collectCount;
    @Expose
    private String description;
    @SerializedName("download_count")
    private Long downloadCount;
    @Expose
    private String filename;
    @Expose
    private String hash;
    @SerializedName("history_heat")
    private Long historyHeat;
    @Expose
    private String imgurl;
    @Expose
    private String intro;
    @SerializedName("is_short")
    private Long isShort;
    @SerializedName("is_ugc")
    private Long isUgc;
    @SerializedName("mkv_sd_hash")
    private String mkvSdHash;
    @Expose
    private String remark;
    @Expose
    private String singername;
    @Expose
    private List<Tag> tags;
    @Expose
    private String update;
    @SerializedName("user_avatar")
    private String userAvatar;
    @Expose
    private Long userid;
    @Expose
    private String username;
    @SerializedName("video_id")
    private Long videoId;
    @SerializedName("video_scale")
    private VideoScale videoScale;
    @Expose
    private String videoname;

    public AudioInfo getAudioInfo() {
        return audioInfo;
    }

    public void setAudioInfo(AudioInfo audioInfo) {
        this.audioInfo = audioInfo;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Long getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Long collectCount) {
        this.collectCount = collectCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getHistoryHeat() {
        return historyHeat;
    }

    public void setHistoryHeat(Long historyHeat) {
        this.historyHeat = historyHeat;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getIsShort() {
        return isShort;
    }

    public void setIsShort(Long isShort) {
        this.isShort = isShort;
    }

    public Long getIsUgc() {
        return isUgc;
    }

    public void setIsUgc(Long isUgc) {
        this.isUgc = isUgc;
    }

    public String getMkvSdHash() {
        return mkvSdHash;
    }

    public void setMkvSdHash(String mkvSdHash) {
        this.mkvSdHash = mkvSdHash;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public VideoScale getVideoScale() {
        return videoScale;
    }

    public void setVideoScale(VideoScale videoScale) {
        this.videoScale = videoScale;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

}
