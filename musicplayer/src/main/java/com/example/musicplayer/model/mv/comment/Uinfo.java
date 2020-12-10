
package com.example.musicplayer.model.mv.comment;


import com.google.gson.annotations.Expose;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Uinfo implements Serializable {

    @Expose
    private String acolor;
    @Expose
    private String icolor;
    @Expose
    private Long ostr;
    @Expose
    private String v;

    public String getAcolor() {
        return acolor;
    }

    public void setAcolor(String acolor) {
        this.acolor = acolor;
    }

    public String getIcolor() {
        return icolor;
    }

    public void setIcolor(String icolor) {
        this.icolor = icolor;
    }

    public Long getOstr() {
        return ostr;
    }

    public void setOstr(Long ostr) {
        this.ostr = ostr;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

}
