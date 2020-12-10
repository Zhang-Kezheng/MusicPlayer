/**
  * Copyright 2020 bejson.com 
  */
package com.example.musicplayer.model.lrc;

import java.io.Serializable;

/**
 * Auto-generated: 2020-10-14 17:31:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetLyricsData implements Serializable {

    private String content;
    private String info;
    private int status;
    private String charset;
    private String fmt;
    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setInfo(String info) {
         this.info = info;
     }
     public String getInfo() {
         return info;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setCharset(String charset) {
         this.charset = charset;
     }
     public String getCharset() {
         return charset;
     }

    public void setFmt(String fmt) {
         this.fmt = fmt;
     }
     public String getFmt() {
         return fmt;
     }

}