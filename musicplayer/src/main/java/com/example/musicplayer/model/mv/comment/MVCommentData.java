
package com.example.musicplayer.model.mv.comment;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class MVCommentData implements Serializable {

    @Expose
    private Long childrenid;
    @Expose
    private java.util.List<Object> config;
    @Expose
    private Long count;
    @SerializedName("current_page")
    private Long currentPage;
    @SerializedName("err_code")
    private Long errCode;
    @Expose
    private Boolean hasRcmd;
    @Expose
    private java.util.List<List> list;
    @Expose
    private String message;
    @Expose
    private String msg;
    @Expose
    private Long status;
    @Expose
    private String weightList;

    public Long getChildrenid() {
        return childrenid;
    }

    public void setChildrenid(Long childrenid) {
        this.childrenid = childrenid;
    }

    public java.util.List<Object> getConfig() {
        return config;
    }

    public void setConfig(java.util.List<Object> config) {
        this.config = config;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getErrCode() {
        return errCode;
    }

    public void setErrCode(Long errCode) {
        this.errCode = errCode;
    }

    public Boolean getHasRcmd() {
        return hasRcmd;
    }

    public void setHasRcmd(Boolean hasRcmd) {
        this.hasRcmd = hasRcmd;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getWeightList() {
        return weightList;
    }

    public void setWeightList(String weightList) {
        this.weightList = weightList;
    }

}
