
package com.example.musicplayer.model.mv.comment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Vinfo9 implements Serializable {

    @SerializedName("actor_status")
    private Long actorStatus;
    @SerializedName("auth_info")
    private String authInfo;
    @SerializedName("biz_status")
    private Long bizStatus;
    @SerializedName("cmt_talent_status")
    private Long cmtTalentStatus;
    @Expose
    private Long justiceLeague;
    @Expose
    private String pic;
    @SerializedName("star_v_status")
    private Long starVStatus;
    @SerializedName("student_status")
    private Long studentStatus;
    @SerializedName("tme_star_status")
    private Long tmeStarStatus;
    @SerializedName("user_label")
    private String userLabel;

    public Long getActorStatus() {
        return actorStatus;
    }

    public void setActorStatus(Long actorStatus) {
        this.actorStatus = actorStatus;
    }

    public String getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    public Long getBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(Long bizStatus) {
        this.bizStatus = bizStatus;
    }

    public Long getCmtTalentStatus() {
        return cmtTalentStatus;
    }

    public void setCmtTalentStatus(Long cmtTalentStatus) {
        this.cmtTalentStatus = cmtTalentStatus;
    }

    public Long getJusticeLeague() {
        return justiceLeague;
    }

    public void setJusticeLeague(Long justiceLeague) {
        this.justiceLeague = justiceLeague;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Long getStarVStatus() {
        return starVStatus;
    }

    public void setStarVStatus(Long starVStatus) {
        this.starVStatus = starVStatus;
    }

    public Long getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(Long studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Long getTmeStarStatus() {
        return tmeStarStatus;
    }

    public void setTmeStarStatus(Long tmeStarStatus) {
        this.tmeStarStatus = tmeStarStatus;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

}
