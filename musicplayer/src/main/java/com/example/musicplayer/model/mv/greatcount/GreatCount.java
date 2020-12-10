package com.example.musicplayer.model.mv.greatcount;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 章可政
 * @date 2020/10/30 23:48
 */
public class GreatCount implements Serializable {

        private String msg;
        private Map<String,GreatStatus> list;
        private int status;
        private int err_code;
        private String message;
        public void setMsg(String msg) {
            this.msg = msg;
        }
        public String getMsg() {
            return msg;
        }

    public Map<String, GreatStatus> getList() {
        return list;
    }

    public void setList(Map<String, GreatStatus> list) {
        this.list = list;
    }

    public void setStatus(int status) {
            this.status = status;
        }
        public int getStatus() {
            return status;
        }

        public void setErr_code(int err_code) {
            this.err_code = err_code;
        }
        public int getErr_code() {
            return err_code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

}
