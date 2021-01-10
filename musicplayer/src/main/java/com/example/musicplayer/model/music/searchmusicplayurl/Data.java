
package com.example.musicplayer.model.music.searchmusicplayurl;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class Data  extends LitePalSupport implements Serializable {

    @SerializedName("album_id")
    private String albumId;
    @SerializedName("album_name")
    private String albumName;
    @SerializedName("audio_id")
    private String audioId;
    @SerializedName("audio_name")
    private String audioName;
    @SerializedName("author_id")
    private String authorId;
    @SerializedName("author_name")
    private String authorName;
    @Expose
    private List<Author> authors;
    @Expose
    private Long bitrate;
    @Expose
    private Long filesize;
    @Expose
    private String hash;
    @SerializedName("have_album")
    private Long haveAlbum;
    @SerializedName("have_mv")
    private Long haveMv;
    @Expose
    private String img;
    @SerializedName("is_free_part")
    private Long isFreePart;
    @Expose
    private String lyrics;
    @SerializedName("play_backup_url")
    private String playBackupUrl;
    @SerializedName("play_url")
    private String playUrl;
    @Expose
    private Long privilege;
    @Expose
    private String privilege2;
    @SerializedName("recommend_album_id")
    private String recommendAlbumId;
    @SerializedName("song_name")
    private String songName;
    @Expose
    private Long timelength;
    @SerializedName("trans_param")
    private TransParam transParam;
    @SerializedName("video_id")
    private String videoId;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public void setBitrate(Long bitrate) {
        this.bitrate = bitrate;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getHaveAlbum() {
        return haveAlbum;
    }

    public void setHaveAlbum(Long haveAlbum) {
        this.haveAlbum = haveAlbum;
    }

    public Long getHaveMv() {
        return haveMv;
    }

    public void setHaveMv(Long haveMv) {
        this.haveMv = haveMv;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getIsFreePart() {
        return isFreePart;
    }

    public void setIsFreePart(Long isFreePart) {
        this.isFreePart = isFreePart;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getPlayBackupUrl() {
        return playBackupUrl;
    }

    public void setPlayBackupUrl(String playBackupUrl) {
        this.playBackupUrl = playBackupUrl;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public Long getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Long privilege) {
        this.privilege = privilege;
    }

    public String getPrivilege2() {
        return privilege2;
    }

    public void setPrivilege2(String privilege2) {
        this.privilege2 = privilege2;
    }

    public String getRecommendAlbumId() {
        return recommendAlbumId;
    }

    public void setRecommendAlbumId(String recommendAlbumId) {
        this.recommendAlbumId = recommendAlbumId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Long getTimelength() {
        return timelength;
    }

    public void setTimelength(Long timelength) {
        this.timelength = timelength;
    }

    public TransParam getTransParam() {
        return transParam;
    }

    public void setTransParam(TransParam transParam) {
        this.transParam = transParam;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}
