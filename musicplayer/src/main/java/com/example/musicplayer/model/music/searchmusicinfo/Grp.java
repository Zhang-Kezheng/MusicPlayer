
package com.example.musicplayer.model.music.searchmusicinfo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grp implements Serializable {

    @SerializedName("A320Privilege")
    private Long a320Privilege;
    @SerializedName("ASQPrivilege")
    private Long aSQPrivilege;
    @SerializedName("Accompany")
    private Long accompany;
    @SerializedName("AlbumID")
    private String albumID;
    @SerializedName("AlbumName")
    private String albumName;
    @SerializedName("AlbumPrivilege")
    private Long albumPrivilege;
    @SerializedName("AudioCdn")
    private Long audioCdn;
    @SerializedName("Audioid")
    private Long audioid;
    @SerializedName("Auxiliary")
    private String auxiliary;
    @SerializedName("Bitrate")
    private Long bitrate;
    @SerializedName("Category")
    private Long category;
    @SerializedName("Duration")
    private Long duration;
    @SerializedName("ExtName")
    private String extName;
    @SerializedName("FailProcess")
    private Long failProcess;
    @SerializedName("FileHash")
    private String fileHash;
    @SerializedName("FileName")
    private String fileName;
    @SerializedName("FileSize")
    private Long fileSize;
    @SerializedName("HQBitrate")
    private Long hQBitrate;
    @SerializedName("HQDuration")
    private Long hQDuration;
    @SerializedName("HQExtName")
    private String hQExtName;
    @SerializedName("HQFailProcess")
    private Long hQFailProcess;
    @SerializedName("HQFileHash")
    private String hQFileHash;
    @SerializedName("HQFileSize")
    private Long hQFileSize;
    @SerializedName("HQPayType")
    private Long hQPayType;
    @SerializedName("HQPkgPrice")
    private Long hQPkgPrice;
    @SerializedName("HQPrice")
    private Long hQPrice;
    @SerializedName("HQPrivilege")
    private Long hQPrivilege;
    @SerializedName("HasAlbum")
    private Long hasAlbum;
    @SerializedName("HiFiQuality")
    private Long hiFiQuality;
    @SerializedName("ID")
    private String iD;
    @SerializedName("IsOriginal")
    private Long isOriginal;
    @SerializedName("M4aSize")
    private Long m4aSize;
    @SerializedName("MixSongID")
    private String mixSongID;
    @SerializedName("MvHash")
    private String mvHash;
    @Expose
    private Long mvTotal;
    @SerializedName("MvTrac")
    private Long mvTrac;
    @SerializedName("MvType")
    private Long mvType;
    @SerializedName("OldCpy")
    private Long oldCpy;
    @SerializedName("OriOtherName")
    private String oriOtherName;
    @SerializedName("OriSongName")
    private String oriSongName;
    @SerializedName("OtherName")
    private String otherName;
    @SerializedName("OwnerCount")
    private Long ownerCount;
    @SerializedName("PayType")
    private Long payType;
    @SerializedName("PkgPrice")
    private Long pkgPrice;
    @SerializedName("Price")
    private Long price;
    @SerializedName("Privilege")
    private Long privilege;
    @SerializedName("Publish")
    private Long publish;
    @SerializedName("PublishAge")
    private Long publishAge;
    @SerializedName("PublishTime")
    private String publishTime;
    @SerializedName("QualityLevel")
    private Long qualityLevel;
    @SerializedName("recommend_type")
    private Long recommendType;
    @SerializedName("ResBitrate")
    private Long resBitrate;
    @SerializedName("ResDuration")
    private Long resDuration;
    @SerializedName("ResFileHash")
    private String resFileHash;
    @SerializedName("ResFileSize")
    private Long resFileSize;
    @SerializedName("SQBitrate")
    private Long sQBitrate;
    @SerializedName("SQDuration")
    private Long sQDuration;
    @SerializedName("SQExtName")
    private String sQExtName;
    @SerializedName("SQFailProcess")
    private Long sQFailProcess;
    @SerializedName("SQFileHash")
    private String sQFileHash;
    @SerializedName("SQFileSize")
    private Long sQFileSize;
    @SerializedName("SQPayType")
    private Long sQPayType;
    @SerializedName("SQPkgPrice")
    private Long sQPkgPrice;
    @SerializedName("SQPrice")
    private Long sQPrice;
    @SerializedName("SQPrivilege")
    private Long sQPrivilege;
    @SerializedName("Scid")
    private Long scid;
    @SerializedName("SingerId")
    private List<Long> singerId;
    @SerializedName("SingerName")
    private String singerName;
    @SerializedName("SongLabel")
    private String songLabel;
    @SerializedName("SongName")
    private String songName;
    @SerializedName("Source")
    private String source;
    @SerializedName("SourceID")
    private Long sourceID;
    @SerializedName("Suffix")
    private String suffix;
    @SerializedName("SuperBitrate")
    private Long superBitrate;
    @SerializedName("SuperDuration")
    private Long superDuration;
    @SerializedName("SuperExtName")
    private String superExtName;
    @SerializedName("SuperFileHash")
    private String superFileHash;
    @SerializedName("SuperFileSize")
    private Long superFileSize;
    @SerializedName("TagContent")
    private String tagContent;
    @SerializedName("TopicRemark")
    private String topicRemark;
    @SerializedName("TopicUrl")
    private String topicUrl;
    @SerializedName("trans_param")
    private TransParam transParam;
    @SerializedName("Type")
    private String type;
    @SerializedName("Uploader")
    private String uploader;

    public Long getA320Privilege() {
        return a320Privilege;
    }

    public void setA320Privilege(Long a320Privilege) {
        this.a320Privilege = a320Privilege;
    }

    public Long getASQPrivilege() {
        return aSQPrivilege;
    }

    public void setASQPrivilege(Long aSQPrivilege) {
        this.aSQPrivilege = aSQPrivilege;
    }

    public Long getAccompany() {
        return accompany;
    }

    public void setAccompany(Long accompany) {
        this.accompany = accompany;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Long getAlbumPrivilege() {
        return albumPrivilege;
    }

    public void setAlbumPrivilege(Long albumPrivilege) {
        this.albumPrivilege = albumPrivilege;
    }

    public Long getAudioCdn() {
        return audioCdn;
    }

    public void setAudioCdn(Long audioCdn) {
        this.audioCdn = audioCdn;
    }

    public Long getAudioid() {
        return audioid;
    }

    public void setAudioid(Long audioid) {
        this.audioid = audioid;
    }

    public String getAuxiliary() {
        return auxiliary;
    }

    public void setAuxiliary(String auxiliary) {
        this.auxiliary = auxiliary;
    }

    public Long getBitrate() {
        return bitrate;
    }

    public void setBitrate(Long bitrate) {
        this.bitrate = bitrate;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public Long getFailProcess() {
        return failProcess;
    }

    public void setFailProcess(Long failProcess) {
        this.failProcess = failProcess;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getHQBitrate() {
        return hQBitrate;
    }

    public void setHQBitrate(Long hQBitrate) {
        this.hQBitrate = hQBitrate;
    }

    public Long getHQDuration() {
        return hQDuration;
    }

    public void setHQDuration(Long hQDuration) {
        this.hQDuration = hQDuration;
    }

    public String getHQExtName() {
        return hQExtName;
    }

    public void setHQExtName(String hQExtName) {
        this.hQExtName = hQExtName;
    }

    public Long getHQFailProcess() {
        return hQFailProcess;
    }

    public void setHQFailProcess(Long hQFailProcess) {
        this.hQFailProcess = hQFailProcess;
    }

    public String getHQFileHash() {
        return hQFileHash;
    }

    public void setHQFileHash(String hQFileHash) {
        this.hQFileHash = hQFileHash;
    }

    public Long getHQFileSize() {
        return hQFileSize;
    }

    public void setHQFileSize(Long hQFileSize) {
        this.hQFileSize = hQFileSize;
    }

    public Long getHQPayType() {
        return hQPayType;
    }

    public void setHQPayType(Long hQPayType) {
        this.hQPayType = hQPayType;
    }

    public Long getHQPkgPrice() {
        return hQPkgPrice;
    }

    public void setHQPkgPrice(Long hQPkgPrice) {
        this.hQPkgPrice = hQPkgPrice;
    }

    public Long getHQPrice() {
        return hQPrice;
    }

    public void setHQPrice(Long hQPrice) {
        this.hQPrice = hQPrice;
    }

    public Long getHQPrivilege() {
        return hQPrivilege;
    }

    public void setHQPrivilege(Long hQPrivilege) {
        this.hQPrivilege = hQPrivilege;
    }

    public Long getHasAlbum() {
        return hasAlbum;
    }

    public void setHasAlbum(Long hasAlbum) {
        this.hasAlbum = hasAlbum;
    }

    public Long getHiFiQuality() {
        return hiFiQuality;
    }

    public void setHiFiQuality(Long hiFiQuality) {
        this.hiFiQuality = hiFiQuality;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public Long getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Long isOriginal) {
        this.isOriginal = isOriginal;
    }

    public Long getM4aSize() {
        return m4aSize;
    }

    public void setM4aSize(Long m4aSize) {
        this.m4aSize = m4aSize;
    }

    public String getMixSongID() {
        return mixSongID;
    }

    public void setMixSongID(String mixSongID) {
        this.mixSongID = mixSongID;
    }

    public String getMvHash() {
        return mvHash;
    }

    public void setMvHash(String mvHash) {
        this.mvHash = mvHash;
    }

    public Long getMvTotal() {
        return mvTotal;
    }

    public void setMvTotal(Long mvTotal) {
        this.mvTotal = mvTotal;
    }

    public Long getMvTrac() {
        return mvTrac;
    }

    public void setMvTrac(Long mvTrac) {
        this.mvTrac = mvTrac;
    }

    public Long getMvType() {
        return mvType;
    }

    public void setMvType(Long mvType) {
        this.mvType = mvType;
    }

    public Long getOldCpy() {
        return oldCpy;
    }

    public void setOldCpy(Long oldCpy) {
        this.oldCpy = oldCpy;
    }

    public String getOriOtherName() {
        return oriOtherName;
    }

    public void setOriOtherName(String oriOtherName) {
        this.oriOtherName = oriOtherName;
    }

    public String getOriSongName() {
        return oriSongName;
    }

    public void setOriSongName(String oriSongName) {
        this.oriSongName = oriSongName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Long getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(Long ownerCount) {
        this.ownerCount = ownerCount;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public Long getPkgPrice() {
        return pkgPrice;
    }

    public void setPkgPrice(Long pkgPrice) {
        this.pkgPrice = pkgPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Long privilege) {
        this.privilege = privilege;
    }

    public Long getPublish() {
        return publish;
    }

    public void setPublish(Long publish) {
        this.publish = publish;
    }

    public Long getPublishAge() {
        return publishAge;
    }

    public void setPublishAge(Long publishAge) {
        this.publishAge = publishAge;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Long getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(Long qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public Long getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(Long recommendType) {
        this.recommendType = recommendType;
    }

    public Long getResBitrate() {
        return resBitrate;
    }

    public void setResBitrate(Long resBitrate) {
        this.resBitrate = resBitrate;
    }

    public Long getResDuration() {
        return resDuration;
    }

    public void setResDuration(Long resDuration) {
        this.resDuration = resDuration;
    }

    public String getResFileHash() {
        return resFileHash;
    }

    public void setResFileHash(String resFileHash) {
        this.resFileHash = resFileHash;
    }

    public Long getResFileSize() {
        return resFileSize;
    }

    public void setResFileSize(Long resFileSize) {
        this.resFileSize = resFileSize;
    }

    public Long getSQBitrate() {
        return sQBitrate;
    }

    public void setSQBitrate(Long sQBitrate) {
        this.sQBitrate = sQBitrate;
    }

    public Long getSQDuration() {
        return sQDuration;
    }

    public void setSQDuration(Long sQDuration) {
        this.sQDuration = sQDuration;
    }

    public String getSQExtName() {
        return sQExtName;
    }

    public void setSQExtName(String sQExtName) {
        this.sQExtName = sQExtName;
    }

    public Long getSQFailProcess() {
        return sQFailProcess;
    }

    public void setSQFailProcess(Long sQFailProcess) {
        this.sQFailProcess = sQFailProcess;
    }

    public String getSQFileHash() {
        return sQFileHash;
    }

    public void setSQFileHash(String sQFileHash) {
        this.sQFileHash = sQFileHash;
    }

    public Long getSQFileSize() {
        return sQFileSize;
    }

    public void setSQFileSize(Long sQFileSize) {
        this.sQFileSize = sQFileSize;
    }

    public Long getSQPayType() {
        return sQPayType;
    }

    public void setSQPayType(Long sQPayType) {
        this.sQPayType = sQPayType;
    }

    public Long getSQPkgPrice() {
        return sQPkgPrice;
    }

    public void setSQPkgPrice(Long sQPkgPrice) {
        this.sQPkgPrice = sQPkgPrice;
    }

    public Long getSQPrice() {
        return sQPrice;
    }

    public void setSQPrice(Long sQPrice) {
        this.sQPrice = sQPrice;
    }

    public Long getSQPrivilege() {
        return sQPrivilege;
    }

    public void setSQPrivilege(Long sQPrivilege) {
        this.sQPrivilege = sQPrivilege;
    }

    public Long getScid() {
        return scid;
    }

    public void setScid(Long scid) {
        this.scid = scid;
    }

    public List<Long> getSingerId() {
        return singerId;
    }

    public void setSingerId(List<Long> singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongLabel() {
        return songLabel;
    }

    public void setSongLabel(String songLabel) {
        this.songLabel = songLabel;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getSourceID() {
        return sourceID;
    }

    public void setSourceID(Long sourceID) {
        this.sourceID = sourceID;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getSuperBitrate() {
        return superBitrate;
    }

    public void setSuperBitrate(Long superBitrate) {
        this.superBitrate = superBitrate;
    }

    public Long getSuperDuration() {
        return superDuration;
    }

    public void setSuperDuration(Long superDuration) {
        this.superDuration = superDuration;
    }

    public String getSuperExtName() {
        return superExtName;
    }

    public void setSuperExtName(String superExtName) {
        this.superExtName = superExtName;
    }

    public String getSuperFileHash() {
        return superFileHash;
    }

    public void setSuperFileHash(String superFileHash) {
        this.superFileHash = superFileHash;
    }

    public Long getSuperFileSize() {
        return superFileSize;
    }

    public void setSuperFileSize(Long superFileSize) {
        this.superFileSize = superFileSize;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }

    public String getTopicRemark() {
        return topicRemark;
    }

    public void setTopicRemark(String topicRemark) {
        this.topicRemark = topicRemark;
    }

    public String getTopicUrl() {
        return topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }

    public TransParam getTransParam() {
        return transParam;
    }

    public void setTransParam(TransParam transParam) {
        this.transParam = transParam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

}
