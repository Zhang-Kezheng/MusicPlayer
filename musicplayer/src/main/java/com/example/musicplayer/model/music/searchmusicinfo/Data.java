
package com.example.musicplayer.model.music.searchmusicinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Data implements Serializable {

    @Expose
    private java.util.List<Aggregation> aggregation;
    @Expose
    private Long allowerr;
    @Expose
    private Long chinesecount;
    @Expose
    private Long correctionforce;
    @Expose
    private String correctionsubject;
    @Expose
    private String correctiontip;
    @Expose
    private Long correctiontype;
    @Expose
    private Long isshareresult;
    @Expose
    private Long istag;
    @Expose
    private Long istagresult;
    @Expose
    private java.util.List<com.example.musicplayer.model.music.searchmusicinfo.List> lists;
    @Expose
    private Long page;
    @Expose
    private Long pagesize;
    @Expose
    private Long searchfull;
    @SerializedName("sectag_info")
    private SectagInfo sectagInfo;
    @Expose
    private Long subjecttype;
    @Expose
    private Long total;

    public java.util.List<Aggregation> getAggregation() {
        return aggregation;
    }

    public void setAggregation(java.util.List<Aggregation> aggregation) {
        this.aggregation = aggregation;
    }

    public Long getAllowerr() {
        return allowerr;
    }

    public void setAllowerr(Long allowerr) {
        this.allowerr = allowerr;
    }

    public Long getChinesecount() {
        return chinesecount;
    }

    public void setChinesecount(Long chinesecount) {
        this.chinesecount = chinesecount;
    }

    public Long getCorrectionforce() {
        return correctionforce;
    }

    public void setCorrectionforce(Long correctionforce) {
        this.correctionforce = correctionforce;
    }

    public String getCorrectionsubject() {
        return correctionsubject;
    }

    public void setCorrectionsubject(String correctionsubject) {
        this.correctionsubject = correctionsubject;
    }

    public String getCorrectiontip() {
        return correctiontip;
    }

    public void setCorrectiontip(String correctiontip) {
        this.correctiontip = correctiontip;
    }

    public Long getCorrectiontype() {
        return correctiontype;
    }

    public void setCorrectiontype(Long correctiontype) {
        this.correctiontype = correctiontype;
    }

    public Long getIsshareresult() {
        return isshareresult;
    }

    public void setIsshareresult(Long isshareresult) {
        this.isshareresult = isshareresult;
    }

    public Long getIstag() {
        return istag;
    }

    public void setIstag(Long istag) {
        this.istag = istag;
    }

    public Long getIstagresult() {
        return istagresult;
    }

    public void setIstagresult(Long istagresult) {
        this.istagresult = istagresult;
    }

    public java.util.List<com.example.musicplayer.model.music.searchmusicinfo.List> getLists() {
        return lists;
    }

    public void setLists(java.util.List<com.example.musicplayer.model.music.searchmusicinfo.List> lists) {
        this.lists = lists;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getPagesize() {
        return pagesize;
    }

    public void setPagesize(Long pagesize) {
        this.pagesize = pagesize;
    }

    public Long getSearchfull() {
        return searchfull;
    }

    public void setSearchfull(Long searchfull) {
        this.searchfull = searchfull;
    }

    public SectagInfo getSectagInfo() {
        return sectagInfo;
    }

    public void setSectagInfo(SectagInfo sectagInfo) {
        this.sectagInfo = sectagInfo;
    }

    public Long getSubjecttype() {
        return subjecttype;
    }

    public void setSubjecttype(Long subjecttype) {
        this.subjecttype = subjecttype;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
