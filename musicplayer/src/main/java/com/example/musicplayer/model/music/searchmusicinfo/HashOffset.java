
package com.example.musicplayer.model.music.searchmusicinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class HashOffset implements Serializable {

    @SerializedName("end_byte")
    private Long endByte;
    @SerializedName("end_ms")
    private Long endMs;
    @SerializedName("file_type")
    private Long fileType;
    @SerializedName("offset_hash")
    private String offsetHash;
    @SerializedName("start_byte")
    private Long startByte;
    @SerializedName("start_ms")
    private Long startMs;

    public Long getEndByte() {
        return endByte;
    }

    public void setEndByte(Long endByte) {
        this.endByte = endByte;
    }

    public Long getEndMs() {
        return endMs;
    }

    public void setEndMs(Long endMs) {
        this.endMs = endMs;
    }

    public Long getFileType() {
        return fileType;
    }

    public void setFileType(Long fileType) {
        this.fileType = fileType;
    }

    public String getOffsetHash() {
        return offsetHash;
    }

    public void setOffsetHash(String offsetHash) {
        this.offsetHash = offsetHash;
    }

    public Long getStartByte() {
        return startByte;
    }

    public void setStartByte(Long startByte) {
        this.startByte = startByte;
    }

    public Long getStartMs() {
        return startMs;
    }

    public void setStartMs(Long startMs) {
        this.startMs = startMs;
    }

}
