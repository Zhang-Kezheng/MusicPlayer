
package com.example.musicplayer.model.music.searchmusicplayurl;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class SearchMusicPlayUrlData  extends LitePalSupport implements Serializable {

    @Expose
    private Data data;
    @SerializedName("err_code")
    private Long errCode;
    @Expose
    private Long status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Long getErrCode() {
        return errCode;
    }

    public void setErrCode(Long errCode) {
        this.errCode = errCode;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
