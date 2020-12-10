package com.example.musicplayer.model.mv;

import com.example.musicplayer.model.mv.detail.MVDetail;
import com.example.musicplayer.model.mv.playurl.MVModel;

import java.io.Serializable;

/**
 * @author 章可政
 * @date 2020/10/29 18:53
 */
public class MV implements Serializable {
    private MVModel mvModel;
    private MVDetail mvDetail;

    public MV(MVModel mvModel, MVDetail mvDetail) {
        this.mvModel = mvModel;
        this.mvDetail = mvDetail;
    }

    public MV() {
    }

    public MVModel getMvModel() {
        return mvModel;
    }

    public void setMvModel(MVModel mvModel) {
        this.mvModel = mvModel;
    }

    public MVDetail getMvDetail() {
        return mvDetail;
    }

    public void setMvDetail(MVDetail mvDetail) {
        this.mvDetail = mvDetail;
    }
}
