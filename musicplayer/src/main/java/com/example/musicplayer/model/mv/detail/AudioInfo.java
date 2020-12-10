
package com.example.musicplayer.model.mv.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class AudioInfo implements Serializable {

    @SerializedName("album_audio_id")
    private Long albumAudioId;
    @SerializedName("album_id")
    private Long albumId;
    @SerializedName("audio_id")
    private Long audioId;
    @Expose
    private Long bitrate;
    @Expose
    private Long duration;
    @Expose
    private String extname;
    @SerializedName("fail_process")
    private Long failProcess;
    @Expose
    private String filename;
    @Expose
    private Long filesize;
    @SerializedName("filesize_128")
    private Long filesize128;
    @SerializedName("filesize_320")
    private Long filesize320;
    @SerializedName("filesize_sq")
    private Long filesizeSq;
    @Expose
    private String hash;
    @SerializedName("hash_128")
    private String hash128;
    @SerializedName("hash_320")
    private String hash320;
    @SerializedName("hash_sq")
    private String hashSq;
    @Expose
    private String img;
    @SerializedName("is_publish")
    private Long isPublish;
    @SerializedName("old_cpy")
    private Long oldCpy;
    @SerializedName("pay_type")
    private Long payType;
    @Expose
    private Long privilege;
    @SerializedName("privilege_128")
    private Long privilege128;
    @SerializedName("privilege_320")
    private Long privilege320;
    @SerializedName("privilege_sq")
    private Long privilegeSq;
    @Expose
    private String singername;
    @Expose
    private String songname;

    public Long getAlbumAudioId() {
        return albumAudioId;
    }

    public void setAlbumAudioId(Long albumAudioId) {
        this.albumAudioId = albumAudioId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getAudioId() {
        return audioId;
    }

    public void setAudioId(Long audioId) {
        this.audioId = audioId;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public void setBitrate(Long bitrate) {
        this.bitrate = bitrate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public Long getFailProcess() {
        return failProcess;
    }

    public void setFailProcess(Long failProcess) {
        this.failProcess = failProcess;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getFilesize() {
        return filesize;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public Long getFilesize128() {
        return filesize128;
    }

    public void setFilesize128(Long filesize128) {
        this.filesize128 = filesize128;
    }

    public Long getFilesize320() {
        return filesize320;
    }

    public void setFilesize320(Long filesize320) {
        this.filesize320 = filesize320;
    }

    public Long getFilesizeSq() {
        return filesizeSq;
    }

    public void setFilesizeSq(Long filesizeSq) {
        this.filesizeSq = filesizeSq;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash128() {
        return hash128;
    }

    public void setHash128(String hash128) {
        this.hash128 = hash128;
    }

    public String getHash320() {
        return hash320;
    }

    public void setHash320(String hash320) {
        this.hash320 = hash320;
    }

    public String getHashSq() {
        return hashSq;
    }

    public void setHashSq(String hashSq) {
        this.hashSq = hashSq;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Long isPublish) {
        this.isPublish = isPublish;
    }

    public Long getOldCpy() {
        return oldCpy;
    }

    public void setOldCpy(Long oldCpy) {
        this.oldCpy = oldCpy;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public Long getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Long privilege) {
        this.privilege = privilege;
    }

    public Long getPrivilege128() {
        return privilege128;
    }

    public void setPrivilege128(Long privilege128) {
        this.privilege128 = privilege128;
    }

    public Long getPrivilege320() {
        return privilege320;
    }

    public void setPrivilege320(Long privilege320) {
        this.privilege320 = privilege320;
    }

    public Long getPrivilegeSq() {
        return privilegeSq;
    }

    public void setPrivilegeSq(Long privilegeSq) {
        this.privilegeSq = privilegeSq;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

}
