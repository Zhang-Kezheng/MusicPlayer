
package com.example.musicplayer.model.mv.comment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class List implements Serializable {

    @Expose
    private String action;
    @Expose
    private String addtime;
    @SerializedName("author_name")
    private String authorName;
    @Expose
    private String bi;
    @Expose
    private String cmtdreturnserver;
    @Expose
    private String code;
    @Expose
    private String content;
    @Expose
    private String cover;
    @Expose
    private Long delPid;
    @SerializedName("edit_status")
    private Long editStatus;
    @Expose
    private String extdata;
    @Expose
    private String hash;
    @Expose
    private String id;
    @Expose
    private String images;
    @Expose
    private Like like;
    @SerializedName("m_type")
    private Long mType;
    @Expose
    private Long pid;
    @SerializedName("puser_id")
    private Long puserId;
    @Expose
    private String rcmd;
    @Expose
    private Long risk;
    @Expose
    private Long score;
    @SerializedName("special_child_id")
    private String specialChildId;
    @SerializedName("special_child_name")
    private String specialChildName;
    @SerializedName("special_id")
    private String specialId;
    @SerializedName("special_singer")
    private String specialSinger;
    @SerializedName("special_userId")
    private String specialUserId;
    @Expose
    private Long status;
    @Expose
    private String story;
    @Expose
    private Udetail udetail;
    @Expose
    private java.util.List<Uinfo> uinfo;
    @Expose
    private Long unfold;
    @Expose
    private String updatetime;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_pic")
    private String userPic;
    @SerializedName("user_sex")
    private Long userSex;
    @SerializedName("user_ugc")
    private UserUgc userUgc;
    @Expose
    private Boolean userlimit;
    @Expose
    private Vinfo9 vinfo9;
    @SerializedName("vip_type")
    private Long vipType;
    @SerializedName("y_type")
    private Long yType;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getCmtdreturnserver() {
        return cmtdreturnserver;
    }

    public void setCmtdreturnserver(String cmtdreturnserver) {
        this.cmtdreturnserver = cmtdreturnserver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getDelPid() {
        return delPid;
    }

    public void setDelPid(Long delPid) {
        this.delPid = delPid;
    }

    public Long getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Long editStatus) {
        this.editStatus = editStatus;
    }

    public String getExtdata() {
        return extdata;
    }

    public void setExtdata(String extdata) {
        this.extdata = extdata;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }

    public Long getMType() {
        return mType;
    }

    public void setMType(Long mType) {
        this.mType = mType;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getPuserId() {
        return puserId;
    }

    public void setPuserId(Long puserId) {
        this.puserId = puserId;
    }

    public String getRcmd() {
        return rcmd;
    }

    public void setRcmd(String rcmd) {
        this.rcmd = rcmd;
    }

    public Long getRisk() {
        return risk;
    }

    public void setRisk(Long risk) {
        this.risk = risk;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getSpecialChildId() {
        return specialChildId;
    }

    public void setSpecialChildId(String specialChildId) {
        this.specialChildId = specialChildId;
    }

    public String getSpecialChildName() {
        return specialChildName;
    }

    public void setSpecialChildName(String specialChildName) {
        this.specialChildName = specialChildName;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public String getSpecialSinger() {
        return specialSinger;
    }

    public void setSpecialSinger(String specialSinger) {
        this.specialSinger = specialSinger;
    }

    public String getSpecialUserId() {
        return specialUserId;
    }

    public void setSpecialUserId(String specialUserId) {
        this.specialUserId = specialUserId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Udetail getUdetail() {
        return udetail;
    }

    public void setUdetail(Udetail udetail) {
        this.udetail = udetail;
    }

    public java.util.List<Uinfo> getUinfo() {
        return uinfo;
    }

    public void setUinfo(java.util.List<Uinfo> uinfo) {
        this.uinfo = uinfo;
    }

    public Long getUnfold() {
        return unfold;
    }

    public void setUnfold(Long unfold) {
        this.unfold = unfold;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public Long getUserSex() {
        return userSex;
    }

    public void setUserSex(Long userSex) {
        this.userSex = userSex;
    }

    public UserUgc getUserUgc() {
        return userUgc;
    }

    public void setUserUgc(UserUgc userUgc) {
        this.userUgc = userUgc;
    }

    public Boolean getUserlimit() {
        return userlimit;
    }

    public void setUserlimit(Boolean userlimit) {
        this.userlimit = userlimit;
    }

    public Vinfo9 getVinfo9() {
        return vinfo9;
    }

    public void setVinfo9(Vinfo9 vinfo9) {
        this.vinfo9 = vinfo9;
    }

    public Long getVipType() {
        return vipType;
    }

    public void setVipType(Long vipType) {
        this.vipType = vipType;
    }

    public Long getYType() {
        return yType;
    }

    public void setYType(Long yType) {
        this.yType = yType;
    }

}
