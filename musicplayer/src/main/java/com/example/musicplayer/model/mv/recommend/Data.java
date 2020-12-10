
package com.example.musicplayer.model.mv.recommend;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Data implements Serializable {

    @Expose
    private List<Info> info;
    @Expose
    private Long timestamp;
    @Expose
    private Long total;

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
