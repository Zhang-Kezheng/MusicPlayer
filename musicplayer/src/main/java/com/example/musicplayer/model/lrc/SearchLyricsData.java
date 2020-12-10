
package com.example.musicplayer.model.lrc;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SearchLyricsData implements Serializable {

    @Expose
    private List<Candidate> candidates;
    @Expose
    private String companys;
    @Expose
    private Long errcode;
    @Expose
    private String errmsg;
    @Expose
    private Long expire;
    @SerializedName("has_complete_right")
    private  Long hasCompleteRight;
    @Expose
    private String info;
    @Expose
    private String keyword;
    @Expose
    private String proposal;
    @Expose
    private Long status;
    @Expose
    private Long ugc;
    @Expose
    private List<Object> ugccandidates;
    @Expose
    private Long ugccount;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getCompanys() {
        return companys;
    }

    public void setCompanys(String companys) {
        this.companys = companys;
    }

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public Long getHasCompleteRight() {
        return hasCompleteRight;
    }

    public void setHasCompleteRight(Long hasCompleteRight) {
        this.hasCompleteRight = hasCompleteRight;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getUgc() {
        return ugc;
    }

    public void setUgc(Long ugc) {
        this.ugc = ugc;
    }

    public List<Object> getUgccandidates() {
        return ugccandidates;
    }

    public void setUgccandidates(List<Object> ugccandidates) {
        this.ugccandidates = ugccandidates;
    }

    public Long getUgccount() {
        return ugccount;
    }

    public void setUgccount(Long ugccount) {
        this.ugccount = ugccount;
    }

}
