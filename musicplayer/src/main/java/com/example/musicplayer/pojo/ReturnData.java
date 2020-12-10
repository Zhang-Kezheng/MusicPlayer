package com.example.musicplayer.pojo;


import java.io.Serializable;

/**
 * @author 章可政
 * @date 2020/10/16 15:33
 */
public class ReturnData implements Serializable {
    private Integer status;
    private String message;
    private User data;

    public ReturnData() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public ReturnData(Integer status, String message, User data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
